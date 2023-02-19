package com.tang.newcloud.service.chat.controller.api;

import com.tang.newcloud.common.base.result.R;
import com.tang.newcloud.common.base.util.JwtInfo;
import com.tang.newcloud.common.base.util.JwtUtils;
import com.tang.newcloud.service.chat.entity.UserFriend;
import com.tang.newcloud.service.chat.entity.vo.FriendVo;
import com.tang.newcloud.service.chat.service.UserFriendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @program: NewCloud
 * @description:
 * @author: tanglei
 * @create: 2023-02-09 23:00
 **/
@RestController
@RequestMapping("api/user/friend")
@Api(tags = "用户好友")
public class ApiUserFriendController {

    @Resource
    private UserFriendService userFriendService;

    /**
     * //添加好友
     * @param userFriend
     * @return
     */
    @ApiOperation("添加好友")
    @PostMapping("/auth/add")
    public R addFriend(@ApiParam(value = "好友对象",required = true)@RequestBody
                                   UserFriend userFriend){
        int i = userFriendService.saveFriend(userFriend);
        return i>0?R.ok().message("添加成功等待验证"):R.error().message("添加失败请稍后再试");
    }

    /**
     *
     * @param request
     * @return
     */
    @ApiOperation("好友添加查询")
    @GetMapping("auth/read")
    public R askForUser(HttpServletRequest request) throws ExecutionException, InterruptedException {
        JwtInfo token = JwtUtils.getMemberIdByJwtToken(request);
        String userId = token.getId();
        List<FriendVo> friendVoList = userFriendService.getFriendRequest(userId);
        return R.ok().data("friendVoList",friendVoList);
    }

    @ApiOperation("是否同意添加好友")
    @PutMapping("auth/agree/add")
    public R agreeAddFriend(@ApiParam(value = "好友id",required = true)String friendId,
                            HttpServletRequest request,
                            @ApiParam(value = "同意标识符",required = true)Integer type){
        JwtInfo token = JwtUtils.getMemberIdByJwtToken(request);
        if(type==1){
            //同意
            Integer i = userFriendService.agreeAddFriend(token, friendId);
            return i>3?R.ok().message("添加成功"):R.error().message("添加失败");
        }else{
            //不同意
            Integer i = userFriendService.disagreeFriend(token,friendId);
            return i>0?R.ok().message("已拒绝"):R.error().message("操作失败");
        }
    }

    //删除好友
    //查看好友列表
    //查看指定好友信息
}
