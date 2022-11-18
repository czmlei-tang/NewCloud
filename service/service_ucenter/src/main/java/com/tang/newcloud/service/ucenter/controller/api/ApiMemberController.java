package com.tang.newcloud.service.ucenter.controller.api;

import com.tang.newcloud.common.base.result.R;
import com.tang.newcloud.common.base.result.ResultCodeEnum;
import com.tang.newcloud.common.base.util.JwtInfo;
import com.tang.newcloud.common.base.util.JwtUtils;
import com.tang.newcloud.service.base.dto.MemberDto;
import com.tang.newcloud.service.base.exception.NewCloudException;
import com.tang.newcloud.service.ucenter.entity.vo.LoginVo;
import com.tang.newcloud.service.ucenter.entity.vo.RegisterVo;
import com.tang.newcloud.service.ucenter.service.UcenterMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(description = "会员管理")
@RestController
@RequestMapping("/api/ucenter/member")
@Slf4j
public class ApiMemberController {
    @Autowired
    private UcenterMemberService memberService;

    @ApiOperation(value = "会员注册")
    @PostMapping("/register")
    public R register(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return R.ok();
    }

    @ApiOperation(value = "会员登录")
    @PostMapping("/login")
    public R login(@RequestBody LoginVo loginVo) {
        String token = memberService.login(loginVo);
        return R.ok().data("token", token);
    }

    @ApiOperation(value = "根据token获取登录信息")
    @GetMapping("/get-login-info")
    public R getLoginInfo(HttpServletRequest request){

        try{
            JwtInfo jwtInfo = JwtUtils.getMemberIdByJwtToken(request);
            return R.ok().data("userInfo", jwtInfo);
        }catch (Exception e){
            log.error("解析用户信息失败，" + e.getMessage());
            throw new NewCloudException(ResultCodeEnum.FETCH_USERINFO_ERROR);
        }
    }

    @ApiOperation("根据会员id查询会员信息")
    @GetMapping("inner/get-member-dto/{memberId}")
    public MemberDto getMemberDtoByMemberId(
            @ApiParam(value = "会员ID", required = true)
            @PathVariable String memberId){
        MemberDto memberDto = memberService.getMemberDtoByMemberId(memberId);
        return memberDto;
    }

}
