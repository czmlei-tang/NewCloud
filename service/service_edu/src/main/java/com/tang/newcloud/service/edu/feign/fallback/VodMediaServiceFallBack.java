package com.tang.newcloud.service.edu.feign.fallback;

import cn.hutool.json.JSONUtil;
import com.tang.newcloud.common.base.result.R;
import com.tang.newcloud.service.edu.feign.VodMediaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class VodMediaServiceFallBack implements VodMediaService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public R removeVideo(String vodId) {
        rabbitTemplate.convertAndSend("newcloud.oss.vod","newcloud_vod",vodId);
        log.info("熔断保护");
        return R.error();
    }
    @Override
    public R removeVideoByIdList(List<String> videoIdList) {
        String s = JSONUtil.toJsonStr(videoIdList);
        rabbitTemplate.convertAndSend("newcloud.oss.vod","newcloud_vod_batch",s);
        log.info("熔断保护");
        return R.error();
    }
}
