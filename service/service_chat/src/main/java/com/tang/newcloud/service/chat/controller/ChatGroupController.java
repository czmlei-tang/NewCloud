package com.tang.newcloud.service.chat.controller;

import com.tang.newcloud.common.base.result.R;
import com.tang.newcloud.service.chat.entity.ChatGroup;
import com.tang.newcloud.service.chat.service.ChatGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: NewCloud
 * @description: 群组
 * @author: tanglei
 * @create: 2023-02-09 22:52
 **/
@RestController
@RequestMapping("/chat/group")
@Api(tags = "群组管理")
public class ChatGroupController {


}
