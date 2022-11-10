package com.tang.newcloud.service.edu.feign;

import com.tang.newcloud.common.base.result.R;
import com.tang.newcloud.service.edu.feign.fallback.OssFileServiceFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Service
@FeignClient(value = "service-oss",fallback = OssFileServiceFallBack.class)
public interface OssFileService {

    @DeleteMapping("/admin/oss/file/remove")
    R removeFile(@RequestBody String url);

    @GetMapping("/admin/oss/file/test")
    R test();
}