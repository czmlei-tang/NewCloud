package com.tang.newcloud.service.edu.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author : JCccc
 * @CreateTime : 2019/9/3
 * @Description :
 **/
@Configuration
public class RabbitConfig {

    //队列 起名：TestDirectQueue
    @Bean
    public Queue OSSDirectQueue() {
        return new Queue("newcloud_oss",true);
    }

    //队列 起名：TestDirectQueue
    @Bean
    public Queue VODDirectQueue() {
        return new Queue("newcloud_vod",true);
    }

    //队列 起名：TestDirectQueue
    @Bean
    public Queue VODsDirectQueue() {
        return new Queue("newcloud_vod_batch",true);
    }

    @Bean
    public Queue UploadDirectQueue(){
        return new Queue("newcloud_vod_upload",true);
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

    @Bean
    Binding bindingVODDirect() {
        return BindingBuilder.bind(OSSDirectQueue()).to(OSSAndVODDirectExchange()).with("newcloud_vod");
    }

    @Bean
    Binding bindingVODBatchDirect() {
        return BindingBuilder.bind(OSSDirectQueue()).to(OSSAndVODDirectExchange()).with("newcloud_vod_batch");
    }

    @Bean
    Binding bindingVODUploadDirect(){
        return BindingBuilder.bind(UploadDirectQueue()).to(OSSAndVODDirectExchange()).with("newcloud_vod_upload");
    }


}
