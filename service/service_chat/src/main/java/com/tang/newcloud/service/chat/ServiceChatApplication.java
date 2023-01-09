package com.tang.newcloud.service.chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @program: NewCloud
 * @description: 聊天室主启动
 * @author: tanglei
 * @create: 2023-01-06 16:32
 **/
@SpringBootApplication
@ComponentScan({"com.tang.newcloud"})
@EnableDiscoveryClient
@EnableFeignClients
public class ServiceChatApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceChatApplication.class,args);
    }
}
