package com.tang.newcloud.service.chat.controller.api;

import com.tang.newcloud.service.chat.service.SendMessageService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: NewCloud
 * @description:
 * @author: tanglei
 * @create: 2023-02-09 22:59
 **/
@RequestMapping("api/send/message")
@RestController
@Api(tags = "消息")
public class ApiSendMessageController {
    @Resource
    private SendMessageService sendMessageService;

}
