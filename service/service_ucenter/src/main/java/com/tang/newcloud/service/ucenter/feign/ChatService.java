package com.tang.newcloud.service.ucenter.feign;

import com.tang.newcloud.common.base.result.R;
import com.tang.newcloud.service.ucenter.feign.fallback.ChatServiceFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

@Service
@FeignClient(value = "service-chat",fallback = ChatServiceFallBack.class)
public interface ChatService {

    @PostMapping("/chat/friend/add/default")
    R addFriend(String id);
}
