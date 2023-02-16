package com.tang.newcloud.service.chat.utils;

import com.tang.newcloud.common.base.util.SnowFlakeUtil;

/**
 * @program: NewCloud
 * @description:
 * @author: tanglei
 * @create: 2023-02-16 14:31
 **/
public class TransactionNameUtils {
    public static String getTransactionName(){
        return String.valueOf(SnowFlakeUtil.getDefaultSnowFlakeId());
    }
}
