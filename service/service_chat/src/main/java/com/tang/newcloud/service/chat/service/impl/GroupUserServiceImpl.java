package com.tang.newcloud.service.chat.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tang.newcloud.common.base.util.DateUtil;
import com.tang.newcloud.service.chat.entity.GroupUser;
import com.tang.newcloud.service.chat.service.GroupUserService;
import com.tang.newcloud.service.chat.mapper.GroupUserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author 29878
* @description 针对表【group_user】的数据库操作Service实现
* @createDate 2023-02-09 22:22:57
*/
@Service
public class GroupUserServiceImpl extends ServiceImpl<GroupUserMapper, GroupUser>
    implements GroupUserService{
    @Resource
    private GroupUserMapper groupUserMapper;

    @Override
    public Integer inGroup(String groupId, String userId, String remark) {
        GroupUser groupUser = new GroupUser();
        groupUser.setMemberId(userId)
        .setGroupId(groupId)
        .setRemark(remark);

        int i = groupUserMapper.insert(groupUser);
        return i;
    }
}




