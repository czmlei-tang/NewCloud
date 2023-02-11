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
 * @TableName chat_group
 */
@TableName(value ="chat_group")
@Data
@Accessors(chain = true)
public class ChatGroup extends BaseEntity implements Serializable {
    /**
     * 群组id
     */
//    @TableId
//    private String id;

    /**
     * 群组name
     */
    private String name;

    /**
     * 群头像
     */
    private String avatar;

    /**
     * 群组介绍
     */
    private String introduce;

    /**
     * 群主id
     */
    private String groupMasterId;

    /**
     * 群公告
     */
    private String notice;

    /**
     * 进入方式[0:无需验证，1:需要群主或管理员同意]
     */
    private Integer inType;

    /**
     * 群创建时间
     */
//    private Date gmtCreate;

    /**
     * 0正常，1被封
     */
    private Integer status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}