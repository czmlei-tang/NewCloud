package com.tang.newcloud.service.mail.listener;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.tang.newcloud.service.mail.util.BaiduProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RabbitListener(queues = {"newcloud_mail"})
public class MailUtil {
    private final  String MY_EMAIL= "tl2987894459@126.com";

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private BaiduProperties baiduProperties;

    @RabbitHandler
    public  void SendEmail(String mail) {
        SimpleMailMessage message = new SimpleMailMessage();
        JSONObject email = JSONUtil.parseObj(mail);
        String buffer = (String) email.get("buffer");
        String ip = (String)email.get("ip");
        ip = "43.143.129.182";
        log.info("-----------------------------------------------------");
        log.info("-               "+buffer+"                            -");
        log.info("-               "+buffer+"                            -");
        log.info("-               "+ip+"                            -");
        log.info("-----------------------------------------------------");
        String site = getSite(ip);
        log.info("-----------------------------------------------------");
        log.info("-                                                    -");
        log.info("-               "+site+"                            -");
        log.info("-                                                    -");
        log.info("-----------------------------------------------------");
        String now = DateUtil.now();
        message.setSubject("newcloud验证消息"); // 发送邮件的标题
        message.setText("您的账号于"+now+"在电脑端登录。"+site); // 发送邮件的内容
        message.setTo(buffer); // 登录用户的邮箱账号
        message.setFrom(MY_EMAIL); // 发送邮件的邮箱账号，注意一定要和配置文件中的一致！
        sender.send(message); // 调用send方法发送邮件即可
    }
    public String getSite(String ip) {
        String ak = baiduProperties.getAk();
        // 这里调用百度的ip定位api服务 详见 http://api.map.baidu.com/lbsapi/cloud/ip-location-api.htm
        String result = readJsonFromUrl("https://api.map.baidu.com/location/ip?ak="+ak+"&ip="+ip+"&coor=bd09ll");
        JSONObject response = JSONUtil.parseObj(result);
        log.info("-----------------------------------------------------");
        log.info("-                                                    -");
        log.info(response.toString());
        log.info("-                                                    -");
        log.info("-----------------------------------------------------");
        JSONObject content = (JSONObject) response.get("content");
        String address = content.getStr("address");
        return address;
    }

    private String readJsonFromUrl(String url) {
        String result = "";
        result = HttpUtil.get(url, CharsetUtil.CHARSET_UTF_8);
        return result;
    }
}
