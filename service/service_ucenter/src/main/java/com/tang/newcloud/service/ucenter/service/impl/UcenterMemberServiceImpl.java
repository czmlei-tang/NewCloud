package com.tang.newcloud.service.ucenter.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.tang.newcloud.common.base.result.R;
import com.tang.newcloud.common.base.result.ResultCodeEnum;
import com.tang.newcloud.common.base.util.*;
import com.tang.newcloud.service.base.dto.FriendDto;
import com.tang.newcloud.service.base.dto.MemberChatDto;
import com.tang.newcloud.service.base.dto.MemberDto;
import com.tang.newcloud.service.base.exception.NewCloudException;
import com.tang.newcloud.service.ucenter.entity.UcenterMember;
import com.tang.newcloud.service.ucenter.entity.vo.LoginVo;
import com.tang.newcloud.service.ucenter.entity.vo.RegisterVo;
import com.tang.newcloud.service.ucenter.feign.EduService;
import com.tang.newcloud.service.ucenter.service.UcenterMemberService;
import com.tang.newcloud.service.ucenter.mapper.UcenterMemberMapper;
import org.apache.commons.lang.StringUtils;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
* @author 29878
* @description 针对表【ucenter_member(会员表)】的数据库操作Service实现
* @createDate 2022-11-14 15:36:22
*/
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember>
    implements UcenterMemberService{

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Resource
    private UcenterMemberMapper memberMapper;

    @Resource
    private EduService eduService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void register(RegisterVo registerVo) {
        String nickname = registerVo.getNickname();
        String mobile = registerVo.getMobile();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();

        //校验参数
        if (StringUtils.isEmpty(mobile)
                || !FormUtils.isMobile(mobile)
                || StringUtils.isEmpty(password)
                || StringUtils.isEmpty(code)
                || StringUtils.isEmpty(nickname)) {
            throw new NewCloudException(ResultCodeEnum.PARAM_ERROR);
        }
        //比对验证码
        String checkCode = (String)redissonClient.getBucket(mobile).get();
        if(!code.equals(checkCode)){
            throw new NewCloudException(ResultCodeEnum.CODE_ERROR);
        }
        //用户名是否存在
        //手机号是否存在
        Integer count = memberMapper.selectMemberCount(nickname,mobile);
        if(count > 0){
            throw new NewCloudException(ResultCodeEnum.REGISTER_MOBLE_ERROR);
        }
        //注册
        UcenterMember member = new UcenterMember();
        String id = SnowFlakeUtil.getDefaultSnowFlakeId().toString();
        member.setId(id);
        CompletableFuture.runAsync(()->{
            member.setNickname(nickname);
            member.setMobile(mobile);
            member.setPassword(MD5.encrypt(password));
            member.setIsDisabled(0);
            member.setAvatar("https://guli-file-helen.oss-cn-beijing.aliyuncs.com/avatar/default.jpg");
            baseMapper.insert(member);
        });

    }

    @Override
    public String login(LoginVo loginVo,HttpServletRequest request) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        password=MD5.encrypt(password);
        //校验参数
        if (StringUtils.isEmpty(mobile)
                || !FormUtils.isMobile(mobile)
                || StringUtils.isEmpty(password)) {
            throw new NewCloudException(ResultCodeEnum.PARAM_ERROR);
        }
        //核对用户名
        UcenterMember member=memberMapper.selectByMobile(mobile);
        if(member==null) {
            throw new NewCloudException(ResultCodeEnum.LOGIN_PHONE_ERROR);
        }
        //核对密码
        if(!StringUtils.equals(member.getPassword(),password))
            throw new NewCloudException(ResultCodeEnum.LOGIN_PASSWORD_ERROR);
        //核对账户有效性
        if(member.getIsDisabled()==1)
            throw new NewCloudException(ResultCodeEnum.LOGIN_DISABLED_ERROR);
        int type =1;
        memberMapper.updateStatus(type,member.getId());

        if(member.getMail()!=null) {
            HashMap<String, String> mailMap = new HashMap<>();
            String buffer = member.getMail();
            String ip = getIp(request);
            mailMap.put("buffer", buffer);
            mailMap.put("ip", ip);
            mailMap.put("date", DateUtil.now());
            String mail = JSONUtil.toJsonStr(mailMap);
            rabbitTemplate.convertAndSend("newcloud_exchange", "newcloud_mail", mail);
        }
        JwtInfo jwtInfo = new JwtInfo(member.getId(),member.getNickname(), member.getAvatar());
        String jwtToken = JwtUtils.getJwtToken(jwtInfo, 1800);
        //返回token
        return jwtToken;
    }

    /**
     * 获取用户ip地址
     * @return
     */
    public static String getIp(HttpServletRequest request){
        String ip = request.getRemoteAddr();
        return ip;
    }

    @Override
    public void saveWechatMember(UcenterMember member) {
        memberMapper.insert(member);
    }

    @Override
    public UcenterMember getByOpenid(String openid) {
        return memberMapper.selectByOpenid(openid);
    }

    @Override
    public MemberDto getMemberDtoByMemberId(String memberId) {
        return memberMapper.selectMemberDtoByMemberId(memberId);
    }

    @Override
    public Integer countRegisterNum(String day) {
        return memberMapper.selectRegisterNumByDay(day);
    }

    @Override
    public MemberChatDto getMemberNameAndAvatar(String id) {
        MemberChatDto memberChatDto = memberMapper.selectMemberNameAndAvatar(id);
        return memberChatDto;
    }

    @Override
    public FriendDto getFriendParticulars(String friendId) {
        FriendDto friendDto = memberMapper.selectFriendDtoById(friendId);
        return friendDto;
    }

    @Override
    public Map<String,Object> getFriendAvatar(String id) {
        HashMap<String, Object> map = new HashMap<>();
        FriendDto friendDto = memberMapper.selectFriendDtoById(id);
        map.put("avatar",friendDto.getAvatar());
        return map;
    }

    @Override
    public Integer getActive(String memberId) {
        Integer i = memberMapper.selectStatusByMemberId(memberId);
        return i;
    }

    @Override
    public Boolean checkPassword(String oldPassword, HttpServletRequest request) {
        JwtInfo token = JwtUtils.getMemberIdByJwtToken(request);
        String id = token.getId();
        LambdaQueryWrapper<UcenterMember> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StrUtil.isNotEmpty(oldPassword),UcenterMember::getPassword,oldPassword)
                .eq(StrUtil.isNotEmpty(id),UcenterMember::getId,id);
        Integer integer = baseMapper.selectCount(queryWrapper);
        return integer == 1;
    }

    @Override
    public Boolean updatePassword(String password, HttpServletRequest request) {
        JwtInfo token = JwtUtils.getMemberIdByJwtToken(request);
        String id = token.getId();
        UcenterMember ucenterMember = new UcenterMember();
        ucenterMember.setId(id);
        ucenterMember.setPassword(password);
        int i = baseMapper.updateById(ucenterMember);
        return i == 1;
    }

    @Override
    public boolean updateMemberDetail(UcenterMember member) {
        String id = member.getId();
        String nickname = member.getNickname();
        String avatar = member.getAvatar();
        // 判断是否修改用户名
        MemberChatDto memberChatDto = memberMapper.selectMemberNameAndAvatar(id);
        if(memberChatDto.getNickname().equals(nickname)||memberChatDto.getAvatar().equals(avatar)){
            // 更新评论
            memberChatDto.setNickname(nickname).setAvatar(avatar);
            R r = eduService.updateComment(memberChatDto);
            if(r.getSuccess()==true){
                int i = baseMapper.updateById(member);
                return i>0;
            }else{
               throw new NewCloudException(ResultCodeEnum.UNKNOWN_REASON);
            }
        }else{
            int i = baseMapper.updateById(member);
            return i>0;
        }
    }

}




