package com.tang.newcloud.service.ucenter.feign;

import com.tang.newcloud.common.base.result.R;
import com.tang.newcloud.service.base.dto.MemberChatDto;
import com.tang.newcloud.service.ucenter.feign.fallback.ChatServiceFallBack;
import com.tang.newcloud.service.ucenter.feign.fallback.EduServiceFallback;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @program: NewCloud
 * @description:
 * @author: tanglei
 * @create: 2023-05-14 21:11
 **/
@Service
@FeignClient(value = "service-edu",fallback = EduServiceFallback.class)
public interface EduService {

    @PutMapping("/api/edu/comment/update")
    R updateComment(@ApiParam(value = "用户信息对象",required = true) @RequestBody MemberChatDto memberChatDto);
}
