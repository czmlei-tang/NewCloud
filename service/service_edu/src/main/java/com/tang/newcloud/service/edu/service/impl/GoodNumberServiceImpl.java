package com.tang.newcloud.service.edu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tang.newcloud.service.edu.entity.GoodNumber;
import com.tang.newcloud.service.edu.service.GoodNumberService;
import com.tang.newcloud.service.edu.mapper.GoodNumberMapper;
import com.tang.newcloud.service.edu.util.RedisKeyUtils;
import org.redisson.api.*;
import org.redisson.client.codec.StringCodec;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author 29878
* @description 针对表【edu_good_number】的数据库操作Service实现
* @createDate 2023-01-12 16:09:37
*/
@Service
public class GoodNumberServiceImpl extends ServiceImpl<GoodNumberMapper, GoodNumber>
    implements GoodNumberService{

    @Resource
    private RedissonClient redissonClient;

    @Override
    public Boolean saveLiked2Redis(String goodNumberKey) {
        RMap<Object, Object> map = redissonClient.getMap(goodNumberKey);
        Object status = map.put("status", 1);
        if(status!=null){
            //点赞成功时
            incrementLikedCount(RedisKeyUtils.getToid(goodNumberKey));
            //定时任务列表中加入点赞的key
            RSet<String> quartzRm = redissonClient.getSet("quartz-rm",StringCodec.INSTANCE);
            quartzRm.remove(goodNumberKey);
            RSet<String> quartz = redissonClient.getSet("quartz-add",StringCodec.INSTANCE);
            quartz.add(goodNumberKey);
        }
        return status != null;
    }

    /**
     * @description 先点赞才可以取消，1.点赞成功未取消2.点赞成功取消成功3.点赞成功但定时任务执行完，缓存xun
     * 取消未成功，
     * @param goodNumberKey
     * @return
     */
    @Override
    public Boolean unlikeFromRedis(String goodNumberKey) {
        RMap<Object, Object> map = redissonClient.getMap(goodNumberKey);
        Object status = map.put("status", 0);
        if(status!=null){
            //取消点赞成功时
            Long total = decrementLikedCount(RedisKeyUtils.getToid(goodNumberKey));
            if (total==0){
                deleteLikedFromRedis(goodNumberKey);
                deleteCount(RedisKeyUtils.getToid(goodNumberKey));
                RSet<String> quartz = redissonClient.getSet("quartz-add", StringCodec.INSTANCE);
                if(quartz.contains(goodNumberKey)){
                    quartz.remove(goodNumberKey);
                }
                RSet<String> quartzRm = redissonClient.getSet("quartz-rm", StringCodec.INSTANCE);
                quartzRm.add(goodNumberKey);
            }
        }
        return status!=null?true:false;
    }

    @Override
    public Boolean deleteLikedFromRedis(String goodNumberKey) {
        boolean isDelete = redissonClient.getMap(goodNumberKey).delete();
        return isDelete;
    }

    @Override
    public void incrementLikedCount(String toId) {
        RBucket<Long> bucket = redissonClient.getBucket(toId);
        Long aLong = bucket.get();
        if(aLong!=null&&aLong!=0){
            bucket.set(bucket.get()+1);
        }else{
            bucket.set(1L);
        }
    }

    @Override
    public Long decrementLikedCount(String toId) {
        RBucket<Long> bucket = redissonClient.getBucket(toId);
        bucket.set(bucket.get()-1);
        return bucket.get();
    }

    public Boolean deleteCount(String toId){
        boolean delete = redissonClient.getBucket(toId).delete();
        return delete;
    }
}




