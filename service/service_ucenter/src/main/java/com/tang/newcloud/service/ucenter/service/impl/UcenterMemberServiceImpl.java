package com.tang.newcloud.service.ucenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tang.newcloud.common.base.result.ResultCodeEnum;
import com.tang.newcloud.common.base.util.FormUtils;
import com.tang.newcloud.common.base.util.JwtInfo;
import com.tang.newcloud.common.base.util.JwtUtils;
import com.tang.newcloud.common.base.util.MD5;
import com.tang.newcloud.service.base.dto.MemberDto;
import com.tang.newcloud.service.base.exception.NewCloudException;
import com.tang.newcloud.service.ucenter.entity.UcenterMember;
import com.tang.newcloud.service.ucenter.entity.vo.LoginVo;
import com.tang.newcloud.service.ucenter.entity.vo.RegisterVo;
import com.tang.newcloud.service.ucenter.service.UcenterMemberService;
import com.tang.newcloud.service.ucenter.mapper.UcenterMemberMapper;
import org.apache.commons.lang.StringUtils;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

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

    @Resource
    private UcenterMemberMapper memberMapper;

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
        //注册
        UcenterMember member = new UcenterMember();
        member.setNickname(nickname);
        member.setMobile(mobile);
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(0);
        member.setAvatar("https://guli-file-helen.oss-cn-beijing.aliyuncs.com/avatar/default.jpg");
        baseMapper.insert(member);

    }

    @Override
    public String login(LoginVo loginVo) {
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

        JwtInfo jwtInfo = new JwtInfo(member.getId(),member.getNickname(), member.getAvatar());
        String jwtToken = JwtUtils.getJwtToken(jwtInfo, 1800);
        //返回token
        return jwtToken;
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
}




