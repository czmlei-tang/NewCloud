package com.tang.newcloud.service.chat.controller.api;

import com.tang.newcloud.common.base.result.R;
import com.tang.newcloud.common.base.util.JwtInfo;
import com.tang.newcloud.common.base.util.JwtUtils;
import com.tang.newcloud.service.base.dto.FriendDto;
import com.tang.newcloud.service.chat.entity.UserFriend;
import com.tang.newcloud.service.chat.entity.vo.FriendCheckVo;
import com.tang.newcloud.service.chat.entity.vo.FriendListVo;
import com.tang.newcloud.service.chat.feign.UcenterService;
import com.tang.newcloud.service.chat.service.FriendRelationshipService;
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
@RequestMapping("api/chat/friend")
@Api(tags = "用户好友")
public class ApiUserFriendController {

    @Resource
    private UserFriendService userFriendService;

    @Resource
    private FriendRelationshipService relationshipService;

    @Resource
    private UcenterService ucenterService;

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
    @GetMapping("auth/read/check/batch")
    public R askForUser(HttpServletRequest request) throws ExecutionException, InterruptedException {
        JwtInfo token = JwtUtils.getMemberIdByJwtToken(request);
        String userId = token.getId();
        List<FriendCheckVo> friendVoList = userFriendService.getFriendRequest(userId);
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
    @ApiOperation("删除好友")
    @DeleteMapping("/auth/remove")
    public R removeFriend(@ApiParam(value = "好友id",required = true)String friendId,
                          HttpServletRequest request){
        JwtInfo token = JwtUtils.getMemberIdByJwtToken(request);
        String id = token.getId();
        Integer i = userFriendService.removeFriend(id,friendId);
        return i==2?R.ok().message("删除成功"):R.error().message("删除失败");
    }

    /**
     *
     * @param friendId
     * @param request
     * @return
     */
    @ApiOperation("根据id查询好友")
    @GetMapping("/auth/read/{friendId}")
    public R readFriendOne(@ApiParam(value = "好友id",required = true)@PathVariable String friendId,
                           HttpServletRequest request){
        JwtInfo token = JwtUtils.getMemberIdByJwtToken(request);
        String userId = token.getId();
        String remark = userFriendService.getFriendRemark(userId,friendId);

        R r = ucenterService.readFriendParticulars(friendId);
        FriendDto friendDto = (FriendDto)r.getData().get("friendDto");
        friendDto.setRemark(remark);

        return R.ok().data("friendDto",friendDto);
    }
    //查看好友列表
    @ApiOperation("查询all好友")
    @GetMapping("/auth/read/friends")
    public R readFriends(HttpServletRequest request){
        JwtInfo token = JwtUtils.getMemberIdByJwtToken(request);
        String userId = token.getId();
        List<FriendListVo> friends= userFriendService.getFriendRemarks(userId);
        return R.ok().data("friends",friends);
    }
    //修改好友备注
    @ApiOperation("修改好友备注")
    @PutMapping("/auth/update/remark")
    public R changeRemark(@ApiParam(value = "好友id",required = true)String friendId,
                          @ApiParam(value = "新备注",required = true)String remark,
                          HttpServletRequest request){
        JwtInfo token = JwtUtils.getMemberIdByJwtToken(request);
        String userId = token.getId();
        Integer i = relationshipService.updateRemark(userId,friendId,remark);
        return i>0?R.ok().message("修改成功，请返回查看"):R.error().message("修改失败，请稍后再试");
    }

    //修改头像
}
