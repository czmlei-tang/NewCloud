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
 * @TableName group_user
 */
@TableName(value ="group_user")
@Data
@Accessors(chain = true)
public class GroupUser extends BaseEntity implements Serializable {
    /**
     * id
     */
//    @TableId
//    private String id;

    /**
     * 会员id
     */
    private String memberId;

    /**
     * 群id
     */
    private String groupId;

    /**
     * 审核成员id
     */
    private String verifyUserId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 0:普通 1:管理员，2：群主
     */
    private Integer auth;

    /**
     * 创建时间
     */
//    private Date gmtCreate;

    /**
     * 入群时间
     */
//    private Date gmtModified;

    /**
     * 0:正常 1：封禁
     */
    private Integer userStatus;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}