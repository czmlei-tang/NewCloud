package com.tang.newcloud.service.chat.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.tang.newcloud.service.base.model.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 
 * @TableName friend_relationship
 */
@TableName(value ="friend_relationship")
@Data
@Accessors(chain = true)
public class FriendRelationship extends BaseEntity implements Serializable {


    /**
     * 用户id
     */
    private String userId;

    /**
     * 好友id
     */
    private String friendId;

    /**
     * 好友备注
     */
    private String remark;

    /**
     * 1:正常，2:拉黑
     */
    private Integer status;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}