package com.tang.newcloud.service.chat.entity.vo;

import lombok.Data;

import java.util.Date;

/**
 * @program: NewCloud
 * @description:
 * @author: tanglei
 * @create: 2023-04-22 16:37
 **/
@Data
public class UnreadVo {

    /**
     * 群id或者好友id
     */
    private String id;

    /**
     * 群名或好友名
     */
    private String name;

    /**
     * 群或好友（图片）
     */
    private String avatar;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 1.私发 2.群发
     */
    private Integer type;
    /**
     * 未发送数量
     */
    private Integer count;
    /**
     * 消息发送时间
     */
    private Date gmtCreate;

}
