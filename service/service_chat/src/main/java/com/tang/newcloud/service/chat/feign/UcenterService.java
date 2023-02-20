package com.tang.newcloud.service.chat.feign;

import com.tang.newcloud.common.base.result.R;
import com.tang.newcloud.service.chat.feign.fallback.UcenterServiceFallBack;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@FeignClient(value = "service-ucenter",fallback = UcenterServiceFallBack.class)
public interface UcenterService {
    @GetMapping("/admin/ucenter/member/get/{id}")
    R readNameAndAvatar(@PathVariable String id);

    @GetMapping("/admin/ucenter/member/get/friend/{friendId}")
    R readFriendParticulars(@PathVariable String friendId);

    @GetMapping("/admin/ucenter/member/get/friend/avatar/{id}")
    R readFriendAvatar(@PathVariable String id);
}
