package com.tang.newcloud.service.chat.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.tang.newcloud.service.base.model.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 
 * @TableName user_friend
 */
@TableName(value ="user_friend")
@Data
@Accessors(chain = true)
public class UserFriend extends BaseEntity implements Serializable {

    /**
     * 用户id
     */
    private String fromId;

    /**
     * 好友id
     */
    private String toId;

    /**
     * 验证消息
     */
    private String checkMessage;

    /**
     * 备注
     */
    private String remark;

    /**
     * 0:暂未同意，1同意，2不同意
     */
    private Integer status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}