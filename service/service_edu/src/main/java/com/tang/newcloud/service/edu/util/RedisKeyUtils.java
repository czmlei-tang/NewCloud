package com.tang.newcloud.service.edu.util;

/**
 * @program: NewCloud
 * @description:
 * @author: tanglei
 * @create: 2023-01-12 17:04
 **/
public class RedisKeyUtils {

    //保存用户点赞数据的key
    public static final String MAP_KEY_USER_LIKED = "MAP_USER_LIKED";
    //保存用户被点赞数量的key
    public static final String MAP_KEY_USER_LIKED_COUNT = "MAP_USER_LIKED_COUNT";

    /**
     * 拼接被点赞的用户id和点赞的人的id作为key。格式 222222::333333
     *
     * @param toId   被点赞的人id
     * @param fromId 点赞的人的id
     * @return
     */
    public static String getGoodNumberKey(String toId, String fromId) {
        StringBuilder builder = new StringBuilder();
        builder.append(toId);
        builder.append("::");
        builder.append(fromId);
        return builder.toString();
    }

    public static String getToid(String goodNumberKey) {
        String[] split = goodNumberKey.split("::");
        return split[0];
    }

    public static String[] getToidAndFromId(String goodNumberKey) {
        String[] split = goodNumberKey.split("::");
        return split;
    }
}