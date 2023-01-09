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
 * 推荐位
 * @TableName cms_ad_type
 */
@TableName(value ="cms_ad_type")
@Data
@Getter
@Setter
public class AdType extends BaseEntity implements Serializable {

    /**
     * 标题
     */
    private String title;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}