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
 * @TableName user_friend
 */
@TableName(value ="user_friend")
@Data
public class UserFriend extends BaseEntity implements Serializable {
    /**
     * id
     */
//    @TableId
//    private String id;

    /**
     * 用户id
     */
    private String fromId;

    /**
     * 好友id
     */
    private String toId;

    /**
     * 0:暂未同意，1同意，2不同意
     */
    private Integer status;

    /**
     * 发起时间
     */
//    private Date gmtCreate;

    /**
     * 同意或拒绝时间
     */
//    private Date gmtModified;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}