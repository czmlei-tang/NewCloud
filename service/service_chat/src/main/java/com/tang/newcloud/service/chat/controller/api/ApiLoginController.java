package com.tang.newcloud.service.chat.controller.api;

import com.tang.newcloud.service.chat.feign.UcenterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @program: NewCloud
 * @description: 聊天室登录
 * @author: tanglei
 * @create: 2023-02-21 22:05
 **/
@Api(tags = "登录")
@Controller
@Slf4j
@RequestMapping("api/chat")
public class ApiLoginController {
    @Resource
    private UcenterService ucenterService;

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public String chatLogin(@ApiParam(value = "",required = true)String phone,
                            @ApiParam(value = "",required = true)String password){

        return null;
    }
}
