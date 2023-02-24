package com.tang.newcloud.service.chat.controller;

import com.tang.newcloud.common.base.result.R;
import com.tang.newcloud.service.chat.service.GroupUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: NewCloud
 * @description: 群聊成员
 * @author: tanglei
 * @create: 2023-02-09 22:53
 **/
@RestController
@RequestMapping("/chat/user")
@Api(tags = "群成员")
public class GroupUserController {
    @Resource
    private GroupUserService groupUserService;

    @ApiOperation("定时任务设置在线人数")
    @PutMapping("/set/active")
    public R getActive(String groupId){
        Integer i = groupUserService.updateActive(groupId);
        return R.ok().data("i",i);
    }
}
