package com.tang.newcloud.service.sms.util;

/**
 * @program: NewCloud
 * @description: 消息模板
 * @author: tanglei
 * @create: 2023-04-16 21:50
 **/
public class MessageTemplate {
    public static String getTemp(String checkCode){
        return "您当前的验证码为:"+checkCode+",请在5分钟内输入";
    }
}
