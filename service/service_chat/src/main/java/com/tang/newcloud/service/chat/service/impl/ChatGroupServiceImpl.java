package com.tang.newcloud.service.chat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tang.newcloud.common.base.util.SnowFlakeUtil;
import com.tang.newcloud.service.chat.entity.ChatGroup;
import com.tang.newcloud.service.chat.service.ChatGroupService;
import com.tang.newcloud.service.chat.mapper.ChatGroupMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author 29878
* @description 针对表【chat_group】的数据库操作Service实现
* @createDate 2023-02-09 22:22:57
*/
@Service
public class ChatGroupServiceImpl extends ServiceImpl<ChatGroupMapper, ChatGroup>
    implements ChatGroupService{

    @Resource
    private ChatGroupMapper chatGroupMapper;

    @Override
    public ChatGroup saveGroup(String userId, ChatGroup chatGroup) {
        chatGroup.setGroupMasterId(userId).setId(String.valueOf(SnowFlakeUtil.getDefaultSnowFlakeId()));
        int i = chatGroupMapper.insert(chatGroup);
        if(i>0){
            return chatGroup;
        }else{
            return null;
        }
    }

    @Override
    public Integer deleteGroup(String userId, String groupId) {
        LambdaQueryWrapper<ChatGroup> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ChatGroup::getId,groupId);
        int i = chatGroupMapper.delete(queryWrapper);
        return i;
    }

    @Override
    public ChatGroup changeGroup(ChatGroup chatGroup) {
        int i = chatGroupMapper.updateById(chatGroup);
        return i>0?chatGroup:null;
    }

    @Override
    public ChatGroup getGroup(String groupId) {
        ChatGroup chatGroup = chatGroupMapper.selectById(groupId);
        return chatGroup;
    }
}




