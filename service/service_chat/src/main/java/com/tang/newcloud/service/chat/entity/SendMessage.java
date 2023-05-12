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
     * 用户id
     */
    private String userId;

    /**
     * 朋友id or friendId
     */
    private String areaId;

    /**
     * 消息内容
     */
    private String content;

    /**
     *1.私发 2.群发
     */
    private Integer type;

    /**
     * 0:发送未成功 1：反之
     */
    private Integer sendStatus;

    /**
     * 0:未读，已读
     */
    private Integer messStatus;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}