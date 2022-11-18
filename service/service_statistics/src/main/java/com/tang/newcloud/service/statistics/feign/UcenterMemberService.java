package com.tang.newcloud.service.statistics.feign;

import com.tang.newcloud.common.base.result.R;
import com.tang.newcloud.service.statistics.feign.impl.UcenterMemberServiceFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-ucenter",fallback = UcenterMemberServiceFallBack.class)
@Service
public interface UcenterMemberService {

    @GetMapping(value = "/admin/ucenter/member/count-register-num/{day}")
    R countRegisterNum(@PathVariable("day") String day);
}
