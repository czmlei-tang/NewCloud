package com.tang.newcloud.service.chat.feign;

import com.tang.newcloud.common.base.result.R;
import com.tang.newcloud.service.chat.feign.fallback.OssFileServiceFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.RequestBody;

@Service
@FeignClient(value = "service-oss",fallback = OssFileServiceFallBack.class)
public interface OssFileService {

    @DeleteMapping("/admin/oss/file/remove")
    R removeFile(String url);

}