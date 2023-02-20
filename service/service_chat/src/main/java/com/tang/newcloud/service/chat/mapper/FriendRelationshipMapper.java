package com.tang.newcloud.service.chat.mapper;

import com.tang.newcloud.service.chat.entity.FriendRelationship;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author 29878
* @description 针对表【friend_relationship】的数据库操作Mapper
* @createDate 2023-02-19 19:58:36
* @Entity com.tang.newcloud.service.chat.entity.FriendRelationship
*/
public interface FriendRelationshipMapper extends BaseMapper<FriendRelationship> {

    Integer insertFriend(List<FriendRelationship> list);

    Integer deleteByUserIdAndFriendId(String userId, String friendId);

    String selectRemarkById(String userId, String friendId);

    List<FriendRelationship> selectByUserId(String userId);

    Integer updateByUserIdAndFriendIdAndRemark(String userId, String friendId, String remark);
}




