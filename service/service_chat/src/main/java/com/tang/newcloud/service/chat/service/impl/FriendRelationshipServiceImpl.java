package com.tang.newcloud.service.chat.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tang.newcloud.service.chat.entity.FriendRelationship;
import com.tang.newcloud.service.chat.service.FriendRelationshipService;
import com.tang.newcloud.service.chat.mapper.FriendRelationshipMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author 29878
* @description 针对表【friend_relationship】的数据库操作Service实现
* @createDate 2023-02-19 19:58:36
*/
@Service
public class FriendRelationshipServiceImpl extends ServiceImpl<FriendRelationshipMapper, FriendRelationship>
    implements FriendRelationshipService{

    @Resource
    private FriendRelationshipMapper friendRelationshipMapper;

    @Override
    public Integer updateRemark(String userId, String friendId, String remark) {
        return friendRelationshipMapper.updateByUserIdAndFriendIdAndRemark(userId,friendId,remark);
    }
}




