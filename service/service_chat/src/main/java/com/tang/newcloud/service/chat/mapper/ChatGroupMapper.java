package com.tang.newcloud.service.chat.mapper;

import com.tang.newcloud.service.base.dto.GroupDto;
import com.tang.newcloud.service.chat.entity.ChatGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 29878
* @description 针对表【chat_group】的数据库操作Mapper
* @createDate 2023-02-09 22:22:57
* @Entity com.tang.newcloud.service.chat.entity.ChatGroup
*/
public interface ChatGroupMapper extends BaseMapper<ChatGroup> {

    GroupDto selectGroupNameById(String groupId);

    int updateTotalById(String groupId);
}




