package com.tang.newcloud.service.mail.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = {"newcloud_mail"})
public class MailUtil {
    private final String MY_EMAIL= "tl2987894459@126.com";

    @Autowired
    private JavaMailSender sender;

    @RabbitHandler
    public void SendEmail(String email){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("newcloud验证消息"); // 发送邮件的标题
        message.setText("您的账号在电脑端登录。"); // 发送邮件的内容
        message.setTo(email); // 登录用户的邮箱账号
        message.setFrom(MY_EMAIL); // 发送邮件的邮箱账号，注意一定要和配置文件中的一致！
        sender.send(message); // 调用send方法发送邮件即可
    }
}
