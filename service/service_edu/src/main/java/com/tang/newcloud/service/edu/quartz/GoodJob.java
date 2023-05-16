package com.tang.newcloud.service.edu.quartz;

import com.tang.newcloud.common.base.util.SnowFlakeUtil;
import com.tang.newcloud.service.edu.entity.Comment;
import com.tang.newcloud.service.edu.entity.GoodNumber;
import com.tang.newcloud.service.edu.mapper.CommentMapper;
import com.tang.newcloud.service.edu.mapper.GoodNumberMapper;
import com.tang.newcloud.service.edu.util.RedisKeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.redisson.api.RBucket;
import org.redisson.api.RList;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @program: NewCloud
 * @description: 定时任务
 * @author: tanglei
 * @create: 2023-01-12 17:01
 **/
@Slf4j
public class GoodJob extends QuartzJobBean {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private GoodNumberMapper goodNumberMapper;

    @Autowired
    private RedissonClient redissonClient;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        //从redis里面获取数据
        RSet<String> dataIn = redissonClient.getSet("quartz-add",new StringCodec());
        RSet<String> dataOut = redissonClient.getSet("quartz-rm", new StringCodec());
        Set<String> dataIns = dataIn.readAll();
        Set<String> dataOuts = dataOut.readAll();
        String toId = null;
        //add插入数据(多)
        for (String id :
                dataIns) {
            GoodNumber goodNumber = dealWithString(id, 1);
            int isInsert = goodNumberMapper.insert(goodNumber);
            //插入database success
            if(isInsert > 0){
                //写入点赞数
                toId = RedisKeyUtils.getToid(id);
                RBucket<Object> bucket = redissonClient.getBucket(toId);
                commentMapper.updateGoodNumber((Long)bucket.get(),toId);
                //从集合中删除key
                dataIn.remove(id);
            }
        }
        //rm删除数据（少）
        for (String outID:
             dataOuts) {
            GoodNumber goodNumber = dealWithString(outID, 0);
            Integer isDelete = goodNumberMapper.deleteData(goodNumber);
            if(isDelete > 0){
                toId = RedisKeyUtils.getToid(outID);
                RBucket<Object> bucket = redissonClient.getBucket(toId);
                Long total = (Long)bucket.get();
                commentMapper.updateGoodNumber(total!=null?total:0,toId);
                dataOut.remove(outID);
            }
        }
        //控制台打印时间
        String time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
        log.info(time);
    }

    /**
     * @description 处理字符串
     * @param id
     * @param status
     * @return 点赞数据库对象
     */
    private GoodNumber dealWithString(String id,Integer status){
        String[] toidAndFromId = RedisKeyUtils.getToidAndFromId(id);
        String toId = toidAndFromId[0];
        String fromId = toidAndFromId[1];
        GoodNumber goodNumber = new GoodNumber();
        goodNumber.setToId(toId).setStatus(status).setFromId(fromId).setId(SnowFlakeUtil.getDefaultSnowFlakeId().toString());
        return goodNumber;
    }
}
