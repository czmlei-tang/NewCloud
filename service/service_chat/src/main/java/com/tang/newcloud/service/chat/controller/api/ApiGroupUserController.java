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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

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

    @ApiOperation("查看未审核入群消息")
    @GetMapping("auth/read/mes")
    public R readMessage(HttpServletRequest request){
        JwtInfo token = JwtUtils.getMemberIdByJwtToken(request);
        String userId = token.getId();
        List<Map<String, List<GroupUserVo>>> members = groupUserService.readInMes(userId);
        return R.ok().data("members",members);
    }

    @ApiOperation("同意入群消息")
    @PutMapping("auth/agree")
    public R agreeUser(@ApiParam(value = "未审核成员主键id",required = true)String id,
                       HttpServletRequest request){
        JwtInfo token = JwtUtils.getMemberIdByJwtToken(request);
        Integer i = groupUserService.agreeUser(id,token.getId());
        return i>0?R.ok().message("success"):R.error().message("error");
    }

    @ApiOperation("不同意入群")
    @PutMapping("auth/disagree")
    public R disagreeUser(@ApiParam(value = "未审核成员主键id",required = true)String id,
                          HttpServletRequest request){
        JwtInfo token = JwtUtils.getMemberIdByJwtToken(request);
        Integer i = groupUserService.disagreeUser(id,token.getId());
        return i>0?R.ok().message("success"):R.error().message("error");
    }

    @ApiOperation("根据id推出群聊")
    @DeleteMapping("auth/exit")
    public R exitGroup(@ApiParam(value = "群id",required = true)String groupId,
                       @ApiParam(value = "退群方式",required = true)Integer type,
                       @ApiParam(value = "成员id")String memberId,
                       HttpServletRequest request){
        JwtInfo token = JwtUtils.getMemberIdByJwtToken(request);
        String userId = token.getId();
        Integer i = groupUserService.exitGroup(groupId,type,memberId,userId);
        return i>0?R.ok():R.error().message("退群失败");
    }
}
