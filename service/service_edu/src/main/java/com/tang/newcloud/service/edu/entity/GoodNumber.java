package com.tang.newcloud.service.edu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 
 * @TableName edu_good_number
 */
@TableName(value ="edu_good_number")
@Data
@Accessors(chain = true)
public class GoodNumber implements Serializable {
    /**
     * 点赞id
     */
    @TableId
    private String id;

    /**
     * 来自哪个用户
     */
    private String fromId;

    /**
     * 为哪个wenti
     */
    private String toId;

    /**
     * 0/1,取消or点赞
     */
    private Integer status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}