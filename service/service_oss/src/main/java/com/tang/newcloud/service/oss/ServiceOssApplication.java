package com.tang.newcloud.service.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
//(exclude = DataSourceAutoConfiguration.class)//取消数据源自动配置
@ComponentScan({"com.tang.newcloud"})
//@EnableDiscoveryClient
public class ServiceOssApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceOssApplication.class, args);
    }

}