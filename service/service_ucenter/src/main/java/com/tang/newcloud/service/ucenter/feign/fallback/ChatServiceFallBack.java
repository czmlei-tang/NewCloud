package com.tang.newcloud.service.ucenter.feign.fallback;

import com.tang.newcloud.common.base.result.R;
import com.tang.newcloud.service.ucenter.feign.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @program: NewCloud
 * @description:
 * @author: tanglei
 * @create: 2023-04-17 09:10
 **/
@Service
@Slf4j
public class ChatServiceFallBack implements ChatService {
    @Override
    public R addFriend(String id) {
        log.info("熔断保护");
        return R.error();
    }
}
