package com.tang.newcloud.service.oss.util;

import com.tang.newcloud.service.oss.controller.FileController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageHander {

    @Autowired
    private FileController fileController;

    @RabbitListener(queues = {"newcloud_oss"})
    public void remove(String url){
        fileController.removeFile(url);
    }
}
