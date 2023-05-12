package com.tang.newcloud.service.chat.controller;

import com.tang.newcloud.common.base.result.R;
import com.tang.newcloud.service.chat.mapper.UserFriendMapper;
import com.tang.newcloud.service.chat.service.UserFriendService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * @program: NewCloud
 * @description:
 * @author: tanglei
 * @create: 2023-02-09 23:00
 **/
@RestController
@RequestMapping("/chat/friend")
@Api(tags = "用户好友")
public class UserFriendController {
    @Autowired
    private UserFriendService userFriendService;

    @PostMapping("/add/default")
    public R addDefault(String id){
        return null;
    }

}
