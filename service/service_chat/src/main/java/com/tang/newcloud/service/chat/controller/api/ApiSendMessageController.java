package com.tang.newcloud.service.chat.controller.api;

import com.tang.newcloud.common.base.result.R;
import com.tang.newcloud.common.base.util.JwtInfo;
import com.tang.newcloud.common.base.util.JwtUtils;
import com.tang.newcloud.service.chat.entity.vo.UnreadVo;
import com.tang.newcloud.service.chat.service.SendMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @program: NewCloud
 * @description:
 * @author: tanglei
 * @create: 2023-02-09 22:59
 **/
@RequestMapping("api/chat/send")
@RestController
@Api(tags = "消息")
public class ApiSendMessageController {
    @Resource
    private SendMessageService sendMessageService;

    @ApiOperation("用户未读信息")
    @GetMapping("/auth/latest/unread")
    public R unreadMes(HttpServletRequest request){
        JwtInfo token = JwtUtils.getMemberIdByJwtToken(request);
        List<UnreadVo> unreadVoList = sendMessageService.getUnread(token.getId());
        return R.ok().data("unread", unreadVoList);
    }

}
