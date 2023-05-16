package com.tang.newcloud.service.ucenter.feign.fallback;

import com.tang.newcloud.common.base.result.R;
import com.tang.newcloud.service.base.dto.MemberChatDto;
import com.tang.newcloud.service.ucenter.feign.EduService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @program: NewCloud
 * @description:
 * @author: tanglei
 * @create: 2023-05-14 21:12
 **/
@Service
@Slf4j
public class EduServiceFallback implements EduService {
    @Override
    public R updateComment(MemberChatDto memberChatDto) {
        log.info("熔断保护");
        return R.error();
    }
}
