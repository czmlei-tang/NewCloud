package com.tang.newcloud.service.cms.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.tang.newcloud.service.base.model.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 广告推荐
 * @TableName cms_ad
 */
@TableName(value ="cms_ad")
@Data
@Getter
@Setter
public class Ad extends BaseEntity implements Serializable  {

    /**
     * 标题
     */
    private String title;

    /**
     * 类型ID
     */
    private String typeId;

    /**
     * 图片地址
     */
    private String imageUrl;

    /**
     * 背景颜色
     */
    private String color;

    /**
     * 链接地址
     */
    private String linkUrl;

    /**
     * 排序
     */
    private Object sort;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}