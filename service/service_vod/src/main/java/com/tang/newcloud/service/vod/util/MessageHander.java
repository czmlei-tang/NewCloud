package com.tang.newcloud.service.vod.util;

import cn.hutool.json.JSONUtil;
import com.tang.newcloud.service.vod.controller.MediaController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class MessageHander {
    @Autowired
    private MediaController mediaController;

    @RabbitListener(queues = {"newcloud_vod"})
    public void removeVideo(String vodId){
        mediaController.removeVideo(vodId);
    }

    @RabbitListener(queues = {"newcloud_vod_batch"})
    public void removeVideoByIdList(String vodIds){
        List<String> videoIdList = (List<String>) JSONUtil.parseObj(vodIds);
        log.info(videoIdList.toString());
        mediaController.removeVideoByIdList(videoIdList);
    }
}
