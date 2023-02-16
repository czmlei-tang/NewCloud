package com.tang.newcloud.service.chat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tang.newcloud.common.base.util.ExceptionUtils;
import com.tang.newcloud.common.base.util.SnowFlakeUtil;
import com.tang.newcloud.service.chat.entity.ChatGroup;
import com.tang.newcloud.service.chat.entity.GroupUser;
import com.tang.newcloud.service.chat.mapper.GroupUserMapper;
import com.tang.newcloud.service.chat.service.ChatGroupService;
import com.tang.newcloud.service.chat.mapper.ChatGroupMapper;
import com.tang.newcloud.service.chat.utils.TransactionNameUtils;
import com.tang.newcloud.service.chat.utils.TransactionStatusFactory;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

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

    @Resource
    private GroupUserMapper groupUserMapper;

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private DataSourceTransactionManager dataSourceTransactionManager;


    @Override
    public String saveGroup(String userId, ChatGroup chatGroup) {
        String groupId = String.valueOf(SnowFlakeUtil.getDefaultSnowFlakeId());
        chatGroup.setGroupMasterId(userId)
                .setId(groupId);
        int i = chatGroupMapper.insert(chatGroup);
        if(i>0){
            RMap<String, Object> map = redissonClient.getMap(groupId);
            map.put("active",1);
        }
        return i>0?groupId:null;
    }

    @Override
    public Integer deleteGroup(String userId, String groupId) {

        TransactionStatus status = new TransactionStatusFactory()
                    .getTransactionDefinition(TransactionNameUtils.getTransactionName(),TransactionDefinition.PROPAGATION_REQUIRED);
        try {
            //删除群
            LambdaQueryWrapper<ChatGroup> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ChatGroup::getId,groupId);
            int i = chatGroupMapper.delete(queryWrapper);
            //删除群成员
            LambdaQueryWrapper<GroupUser> wapperMaster = new LambdaQueryWrapper<>();
            wapperMaster.eq(GroupUser::getMemberId,userId)
                    .eq(GroupUser::getGroupId,groupId);
            int a = groupUserMapper.delete(wapperMaster);
            return i+a;

        }catch (Exception e) {
            // 错误输入改成日志
            log.error(ExceptionUtils.getMessage(e));
            // 如果发送错误回滚该事物
            dataSourceTransactionManager.rollback(status);
            return 0;
        }
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




