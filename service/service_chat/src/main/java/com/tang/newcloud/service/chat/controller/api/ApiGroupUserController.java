package com.tang.newcloud.service.chat.controller.api;

import com.tang.newcloud.common.base.result.R;
import com.tang.newcloud.common.base.util.JwtInfo;
import com.tang.newcloud.common.base.util.JwtUtils;
import com.tang.newcloud.service.chat.entity.vo.GroupUserVo;
import com.tang.newcloud.service.chat.service.GroupUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @program: NewCloud
 * @description: 群聊成员
 * @author: tanglei
 * @create: 2023-02-09 22:53
 **/
@RestController
@RequestMapping("api/group/user")
@Api(tags = "群成员")
public class ApiGroupUserController {

    @Autowired
    private GroupUserService groupUserService;

    //加入群聊
    @ApiOperation("根据id申请加入群聊")
    @PostMapping("auth/in")
    public R goInGroup(@ApiParam(value = "群id",required = true) String groupId,
                       @ApiParam(value = "备注",required = true) String remark, HttpServletRequest request){
        JwtInfo jwtToken = JwtUtils.getMemberIdByJwtToken(request);
        String userId = jwtToken.getId();
        Integer i = groupUserService.inGroup(groupId,userId,remark);
        return i>0?R.ok().message("等待管理员同意"):R.error().message("添加群聊失败，请稍后再试");
    }

    @ApiOperation("查看入群消息")
    @GetMapping("auth/read/mes")
    public R readMessage(HttpServletRequest request){
        JwtInfo token = JwtUtils.getMemberIdByJwtToken(request);
        String userId = token.getId();
        List<List<GroupUserVo>> members = groupUserService.readInMes(userId);
        return R.ok().data("members",members);
    }

}
