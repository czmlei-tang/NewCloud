package com.tang.newcloud.service.chat.service;

import com.tang.newcloud.common.base.util.JwtInfo;
import com.tang.newcloud.service.chat.entity.UserFriend;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tang.newcloud.service.chat.entity.vo.FriendVo;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
* @author 29878
* @description 针对表【user_friend】的数据库操作Service
* @createDate 2023-02-09 22:22:57
*/
public interface UserFriendService extends IService<UserFriend> {

    int saveFriend(UserFriend userFriend);

    List<FriendVo> getFriendRequest(String userId) throws ExecutionException, InterruptedException;

    Integer agreeAddFriend(JwtInfo token, String fromId);

    Integer disagreeFriend(JwtInfo token, String fromId);
}
