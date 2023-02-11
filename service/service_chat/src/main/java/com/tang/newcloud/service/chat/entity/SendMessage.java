package com.tang.newcloud.service.chat.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.tang.newcloud.service.base.model.BaseEntity;
import lombok.Data;

/**
 * 
 * @TableName send_message
 */
@TableName(value ="send_message")
@Data
public class SendMessage extends BaseEntity implements Serializable {
    /**
     * 消息id
     */
//    @TableId
//    private String id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 群id
     */
    private String groupId;

    /**
     * 朋友id
     */
    private String friendId;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 0:发送未成功 1：反之
     */
    private Integer sendStatus;

    /**
     * 0:未读，已读
     */
    private Integer messStatus;

    /**
     * 消息发送时间
     */
//    private Date gmtCreate;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}