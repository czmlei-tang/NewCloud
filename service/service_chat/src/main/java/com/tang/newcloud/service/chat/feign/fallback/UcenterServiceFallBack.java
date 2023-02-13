package com.tang.newcloud.service.chat.feign.fallback;

import com.tang.newcloud.common.base.result.R;
import com.tang.newcloud.service.chat.feign.UcenterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @program: NewCloud
 * @description:
 * @author: tanglei
 * @create: 2023-01-11 19:11
 **/
@Service
@Slf4j
public class UcenterServiceFallBack implements UcenterService {
    @Override
    public R readNameAndAvatar(String id) {
        log.info("熔断保护");
        return R.error();
    }
}
