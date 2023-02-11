package com.tang.newcloud.service.edu.service;

import com.tang.newcloud.service.edu.entity.GoodNumber;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 29878
* @description 针对表【edu_good_number】的数据库操作Service
* @createDate 2023-01-12 16:09:37
*/
public interface GoodNumberService extends IService<GoodNumber> {
    /**
     * 点赞。状态为
     */Boolean saveLiked2Redis(String goodNumberKey);

    /**
     * 取消点赞。将状态改变为0
     */Boolean unlikeFromRedis(String goodNumberKey);

    /**
     * 从Redis中删除一条点赞数据
     */Boolean deleteLikedFromRedis(String goodNumberKey);

    /**
     * 该用户的点赞数加1
     * @param toId
     */Integer incrementLikedCount(String toId);

    /**
     * 该用户的点赞数减1
     * @param toId
     */Integer decrementLikedCount(String toId);

}
