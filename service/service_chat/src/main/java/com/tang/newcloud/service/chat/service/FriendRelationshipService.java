package com.tang.newcloud.service.chat.service;

import com.tang.newcloud.service.chat.entity.FriendRelationship;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 29878
* @description 针对表【friend_relationship】的数据库操作Service
* @createDate 2023-02-19 19:58:36
*/
public interface FriendRelationshipService extends IService<FriendRelationship> {

    Integer updateRemark(String userId, String friendId, String remark);
}
