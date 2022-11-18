package com.tang.newcloud.service.ucenter.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
//注册
public class RegisterVo implements Serializable {
    private static final long serialVersionUID = 1L;
    //用户名
    private String nickname;
    //手机号
    private String mobile;
    private String password;
    //验证码
    private String code;
}