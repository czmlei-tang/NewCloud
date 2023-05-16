package com.tang.newcloud.service.ucenter.mapper;

import com.tang.newcloud.service.base.dto.FriendDto;
import com.tang.newcloud.service.base.dto.MemberChatDto;
import com.tang.newcloud.service.base.dto.MemberDto;
import com.tang.newcloud.service.ucenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
* @author 29878
* @description 针对表【ucenter_member(会员表)】的数据库操作Mapper
* @createDate 2022-11-14 15:36:22
* @Entity com.tang.newcloud.service.ucenter.entity.UcenterMember
*/
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    Integer selectMemberCount(@Param("nickname") String nickname, @Param("mobile") String mobile);

    UcenterMember selectByMobile(String mobile);

    UcenterMember selectByOpenid(String openid);

    MemberDto selectMemberDtoByMemberId(String memberId);

    //用户注册的数量
    Integer selectRegisterNumByDay(String day);

    //群组申请dto
    MemberChatDto selectMemberNameAndAvatar(String id);

    FriendDto selectFriendDtoById(String friendId);

    void updateStatus(int type,String id);

    Integer selectStatusByMemberId(String memberId);

    Boolean checkNicknameAndAvatar(String nickname, String avatar);
}




