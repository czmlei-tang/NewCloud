package com.tang.newcloud.service.ucenter.controller;

import com.tang.newcloud.common.base.result.R;
import com.tang.newcloud.service.base.dto.FriendDto;
import com.tang.newcloud.service.base.dto.MemberChatDto;
import com.tang.newcloud.service.base.dto.MemberDto;
import com.tang.newcloud.service.ucenter.service.UcenterMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Api(tags = "会员管理")
@RestController
@RequestMapping("/admin/ucenter/member")
public class MemberController {

    @Autowired
    private UcenterMemberService memberService;

    @ApiOperation(value = "根据日期统计注册人数")
    @GetMapping(value = "count-register-num/{day}")
    public R countRegisterNum(
            @ApiParam(name = "day", value = "统计日期")
            @PathVariable String day){
        Integer num = memberService.countRegisterNum(day);
        return R.ok().data("registerNum", num);
    }

    @ApiOperation(value = "根据id获取用户名,头像")
    @GetMapping(value = "/get/{id}")
    public R readNameAndAvatar(@ApiParam(value = "用户id")@PathVariable String id){
        MemberChatDto member = memberService.getMemberNameAndAvatar(id);
        return member!=null?R.ok().data("member",member):R.error().message("用户不存在");
    }
    @ApiOperation(value = "根据id获取好友详情")
    @GetMapping("/get/friend/{friendId}")
    public R readFriendParticulars(@ApiParam(value = "用户id",required = true)@PathVariable String friendId){
        FriendDto friendDto = memberService.getFriendParticulars(friendId);
        return R.ok().data("friendDto",friendDto);
    }
    @ApiOperation(value = "获取avatar")
    @GetMapping("/get/friend/avatar/{id}")
    public R readFriendAvatar(@ApiParam(value = "用户id",required = true)@PathVariable String id){
        Map<String,Object> map= memberService.getFriendAvatar(id);
        return R.ok().data(map);
    }
}
