package com.tang.newcloud.service.chat.controller.api;

import com.tang.newcloud.common.base.result.R;
import com.tang.newcloud.common.base.util.JwtInfo;
import com.tang.newcloud.common.base.util.JwtUtils;
import com.tang.newcloud.service.chat.service.GroupUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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
        return null;
    }

}
