package com.tang.newcloud.service.edu.config;

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

    //vod队列
    @Bean
    public Queue VODDirectQueue() {
        return new Queue("newcloud_vod",true);
    }

    //批量vod对列
    @Bean
    public Queue VODsDirectQueue() {
        return new Queue("newcloud_vod_batch",true);
    }

    //上传视频队列
    @Bean
    public Queue UploadDirectQueue(){
        return new Queue("newcloud_vod_upload",true);
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
