package com.tang.newcloud.service.chat.mapper;

import com.tang.newcloud.service.chat.entity.UserFriend;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author 29878
* @description 针对表【user_friend】的数据库操作Mapper
* @createDate 2023-02-09 22:22:57
* @Entity com.tang.newcloud.service.chat.entity.UserFriend
*/
public interface UserFriendMapper extends BaseMapper<UserFriend> {

    int insertUserFriend(UserFriend userFriend);

    List<UserFriend> selectFriendRequest(String userId);

    Integer selectFriendRequestByFromIdAndToId(String userId, String friendId);

    List<UserFriend> selectFriendReponse(String userId);

    Integer updateByFromIdAndToId(String fromId, String toId,Integer i);

    String selectRemarkById(String fromId, String toId);

}




