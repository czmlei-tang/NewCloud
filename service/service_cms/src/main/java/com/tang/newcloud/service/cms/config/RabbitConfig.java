package com.tang.newcloud.service.cms.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    //队列 起名：TestDirectQueue
    @Bean
    public Queue OSSDirectQueue() {
        return new Queue("newcloud_oss",true);
    }

    //Direct交换机 起名：TestDirectExchange
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
