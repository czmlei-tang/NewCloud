package com.tang.newcloud.service.chat.mapper;

import com.tang.newcloud.service.chat.entity.GroupUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author 29878
* @description 针对表【group_user】的数据库操作Mapper
* @createDate 2023-02-09 22:22:57
* @Entity com.tang.newcloud.service.chat.entity.GroupUser
*/
public interface GroupUserMapper extends BaseMapper<GroupUser> {

    List<GroupUser> selectUsersWillIn(String userId);

    List<GroupUser> selectUsersNotInThegroupById(String groupId);

    GroupUser selectUserNotInThegroupById(String id);

    int deleteByGroupIdAndUserId(String groupId, String userId);

    Integer selectAuthByMemberId(String userId);

    List<GroupUser> selectAllGroupUser(String userId);

    List<GroupUser> selectAllGroupUserByGroupId(String groupId);

    Integer updateStatusById(String id, Integer status);
}




