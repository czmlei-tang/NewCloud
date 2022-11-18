package com.tang.newcloud.service.sms;

import org.junit.jupiter.api.Test;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

@SpringBootTest
public class RedissonTest {

    @Autowired
    RedissonClient redissonClient;

    /**
     * 通用对象桶，可以用来存放任类型的对象
     */
    @Test
    public void RedissonBucket(){

//        ====================操作对象桶来存储对象(同步)====================
//        RBucket<Object> bucket = redissonClient.getBucket("name");
        //设置值为victory，过期时间为3小时
//        bucket.set("victory",30, TimeUnit.HOURS);
//        Object value = bucket.get();
//        System.out.println(value);
//        //通过key取value值
        Object name = redissonClient.getBucket("name").get();
        System.out.println("------------------------------------------------------------");
        System.out.println("------------------------------------------------------------");
        System.out.println(name);
        System.out.println("------------------------------------------------------------");

//        //====================关闭客户端====================
//        redissonClient.shutdown();
    }
}
