package com.tang.newcloud.service.chat.service;

import com.tang.newcloud.service.chat.entity.GroupUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 29878
* @description 针对表【group_user】的数据库操作Service
* @createDate 2023-02-09 22:22:57
*/
public interface GroupUserService extends IService<GroupUser> {

    Integer inGroup(String groupId, String userId, String remark);
}
