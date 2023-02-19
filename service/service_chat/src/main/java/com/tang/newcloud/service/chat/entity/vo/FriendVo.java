package com.tang.newcloud.service.chat.entity.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @program: NewCloud
 * @description:
 * @author: tanglei
 * @create: 2023-02-19 17:07
 **/
@Data
@Accessors(chain = true)
public class FriendVo implements Serializable {
    private String friendId;
    private String friendName;
    private String avatar;
    /**
     * 0:别人请求
     * 1:自己请求
     */
    private Integer type;
    /**
     * 0:未处理
     * 1:已同意
     * 2:未同意
     */
    private Integer status;
}
