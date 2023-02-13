package com.tang.newcloud.service.chat.service;

import com.tang.newcloud.service.chat.entity.ChatGroup;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 29878
* @description 针对表【chat_group】的数据库操作Service
* @createDate 2023-02-09 22:22:57
*/
public interface ChatGroupService extends IService<ChatGroup> {

    String saveGroup(String userId, ChatGroup chatGroup);

    Integer deleteGroup(String userId, String groupId);

    ChatGroup changeGroup(ChatGroup chatGroup);

    ChatGroup getGroup(String groupId);
}
