package com.tang.newcloud.service.sms.controller;

import com.tang.newcloud.common.base.result.R;
import com.tang.newcloud.common.base.result.ResultCodeEnum;
import com.tang.newcloud.common.base.util.FormUtils;
import com.tang.newcloud.common.base.util.RandomUtils;
import com.tang.newcloud.service.base.exception.NewCloudException;
import com.tang.newcloud.service.sms.service.SmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/sms")
@Api(description = "短信管理")
@Slf4j
public class ApiSmsController {

    @Autowired
    private SmsService smsService;

    @Autowired
    private RedissonClient redissonClient;


    @GetMapping("/send/{mobile}")
    @ApiOperation("发送短信")
    public R getCode(@PathVariable String mobile) {

        //校验手机号是否合法
        if(StringUtils.isEmpty(mobile) || !FormUtils.isMobile(mobile)) {
            log.error("请输入正确的手机号码 ");
            throw new NewCloudException(ResultCodeEnum.LOGIN_PHONE_ERROR);
        }

        //生成验证码
        String checkCode = RandomUtils.getFourBitRandom();
        //发送验证码
        smsService.send(mobile, checkCode);
        //将验证码存入redis缓存
        CompletableFuture.runAsync(()->{
            RBucket<String> bucket = redissonClient.getBucket(mobile);
            bucket.set(checkCode,5, TimeUnit.MINUTES);
        });

        log.info(checkCode);
//        redisTemplate.opsForValue().set(mobile, checkCode, 5, TimeUnit.MINUTES);

        return R.ok().message("短信发送成功");
    }
}
