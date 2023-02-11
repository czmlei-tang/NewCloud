package com.tang.newcloud.service.edu.feign;

import com.tang.newcloud.service.edu.feign.fallback.OssFileServiceFallBack;
import com.tang.newcloud.service.edu.feign.fallback.UcenterServiceFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;

@Service
@FeignClient(value = "service-ucenter",fallback = UcenterServiceFallBack.class)
public interface UcenterService {
}
