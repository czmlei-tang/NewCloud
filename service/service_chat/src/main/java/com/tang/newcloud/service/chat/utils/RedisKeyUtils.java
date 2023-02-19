package com.tang.newcloud.service.chat.utils;

/**
 * @program: NewCloud
 * @description:
 * @author: tanglei
 * @create: 2023-01-12 17:04
 **/
public class RedisKeyUtils {

    /**
     * 拼接用户id和好友id作为key。格式 222222::333333
     *
     * @param userId   用户id
     * @param friendId 好友id
     * @return
     */
    public static String getFriendKey(String userId, String friendId) {
        StringBuilder builder = new StringBuilder();
        builder.append(userId);
        builder.append("--");
        builder.append(friendId);
        return builder.toString();
    }

    public static String getUserId(String friendKey) {
        String[] split = friendKey.split("--");
        return split[0];
    }

    public static String[] getUserIdAndFriendId(String friendKey) {
        String[] split = friendKey.split("--");
        return split;
    }
}