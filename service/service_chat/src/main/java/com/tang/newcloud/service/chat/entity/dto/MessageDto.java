package com.tang.newcloud.service.chat.entity.dto;

import lombok.Data;

import java.util.Date;

/**
 * @program: NewCloud
 * @description: 消息体对象
 * @author: tanglei
 * @create: 2023-02-18 16:29
 * {
 *     userId:
 *     toUserId:
 *     groupId:
 *     message:
 *     type:
 *     status:
 *     sendTime:
 * }
 **/
@Data
public class MessageDto {
    /**
     * username 当前用户用户id
     */
    private String userId;

    /**
     *接收用户id，可为空
     */
    private String areaId;

    /**
     * 消息体
     */
    private String message;

    /**
     * 1：私发
     * 2：群发
     */
    private Integer type;

    /**
     * 读取状态
     */
    private String status;

    /**
     * 发送时间
     */
    private Date sendTime;
}
