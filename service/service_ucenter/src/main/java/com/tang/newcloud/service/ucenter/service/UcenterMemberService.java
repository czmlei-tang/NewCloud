package com.tang.newcloud.service.ucenter.service;

import com.tang.newcloud.service.base.dto.FriendDto;
import com.tang.newcloud.service.base.dto.MemberChatDto;
import com.tang.newcloud.service.base.dto.MemberDto;
import com.tang.newcloud.service.ucenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tang.newcloud.service.ucenter.entity.vo.LoginVo;
import com.tang.newcloud.service.ucenter.entity.vo.RegisterVo;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
* @author 29878
* @description 针对表【ucenter_member(会员表)】的数据库操作Service
* @createDate 2022-11-14 15:36:22
*/
public interface UcenterMemberService extends IService<UcenterMember> {
    void register(RegisterVo registerVo);

    String login(LoginVo loginVo, HttpServletRequest request);

    void saveWechatMember(UcenterMember member);

    UcenterMember getByOpenid(String openid);

    MemberDto getMemberDtoByMemberId(String memberId);

    Integer countRegisterNum(String day);

    MemberChatDto getMemberNameAndAvatar(String id);

    FriendDto getFriendParticulars(String friendId);

    Map<String,Object> getFriendAvatar(String id);

    Integer getActive(String memberId);

    Boolean checkPassword(String oldPassword, HttpServletRequest request);

    Boolean updatePassword(String password, HttpServletRequest request);

    boolean updateMemberDetail(UcenterMember member);
}
