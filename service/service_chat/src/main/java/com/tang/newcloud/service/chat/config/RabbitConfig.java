package com.tang.newcloud.service.chat.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author : tanglei
 * @CreateTime : 2022/10/10
 * @Description :消息队列配置
 **/
@Configuration
public class RabbitConfig {

    //oss队列
    @Bean
    public Queue OSSDirectQueue() {
        return new Queue("newcloud_oss",true);
    }


    //Direct交换机 起名：oss和vod交换机
    @Bean
    DirectExchange OSSAndVODDirectExchange() {
        return new DirectExchange("newcloud.oss.vod");
    }

    //绑定  将队列和交换机绑定, 并设置用于匹配键：TestDirectRouting
    @Bean
    Binding bindingOSSDirect() {
        return BindingBuilder.bind(OSSDirectQueue()).to(OSSAndVODDirectExchange()).with("newcloud_oss");
    }


}
