package com.tang.newcloud.service.edu.feign.fallback;

import com.tang.newcloud.common.base.result.R;
import com.tang.newcloud.service.edu.feign.OssFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OssFileServiceFallBack implements OssFileService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public R test() {
        return R.error();
    }

    @Override
    public R removeFile(String url) {
        rabbitTemplate.convertAndSend("newcloud.oss.vod","newcloud_oss",url);
        log.info("熔断保护");
        return R.error();
    }
}