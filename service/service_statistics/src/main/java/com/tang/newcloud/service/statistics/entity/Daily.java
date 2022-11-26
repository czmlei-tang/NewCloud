package com.tang.newcloud.service.statistics.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.tang.newcloud.service.base.model.BaseEntity;
import lombok.Data;

/**
 * 网站统计日数据
 * @TableName statistics_daily
 */
@TableName(value ="statistics_daily")
@Data
public class Daily extends BaseEntity implements Serializable {


    /**
     * 统计日期
     */
    private String dateCalculated;

    /**
     * 注册人数
     */
    private Integer registerNum;

    /**
     * 登录人数
     */
    private Integer loginNum;

    /**
     * 每日播放视频数
     */
    private Integer videoViewNum;

    /**
     * 每日新增课程数
     */
    private Integer courseNum;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}