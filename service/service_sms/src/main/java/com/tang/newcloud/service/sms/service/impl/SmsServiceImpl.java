package com.tang.newcloud.service.sms.service.impl;

import com.tang.newcloud.service.sms.service.SmsService;
import com.tang.newcloud.service.sms.util.MessageTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SmsServiceImpl implements SmsService {

    @Autowired
    private JavaMailSender javaMailSender;


    //发送人
    private String from = "tl2987894459@126.com";
    //标题
    private String subject = "验证码邮件";


    @Override
    public void send(String mobile, String checkCode){
        String mail = mobile+"@139.com";
        String temp = MessageTemplate.getTemp(checkCode);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(mail);
        message.setSubject(subject);
        message.setText(temp);
        javaMailSender.send(message);
    }
}
