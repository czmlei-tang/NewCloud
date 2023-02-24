package com.tang.newcloud.service.chat.quartz;

import com.tang.newcloud.service.chat.controller.GroupUserController;
import com.tang.newcloud.service.chat.mapper.ChatGroupMapper;
import com.tang.newcloud.service.chat.service.ChatGroupService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @program: NewCloud
 * @description: 同步在线任务
 * @author: tanglei
 * @create: 2023-02-21 14:44
 **/
@Slf4j
public class SetActiveJob extends QuartzJobBean {
    @Autowired
    private GroupUserController groupUserController;

    @Autowired
    private ChatGroupMapper chatGroupMapper;

    @Autowired
    private RedissonClient redissonClient;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
//        //所有群聊信息
//        chatGroupMapper.selectById()

    }
}
