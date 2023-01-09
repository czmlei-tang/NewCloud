package com.tang.newcloud.service.edu.entity;

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
 * @TableName edu_article
 */
@TableName(value ="edu_article")
@Data
public class Article extends BaseEntity implements Serializable {

    /**
     * 会员id
     */
    private String memberId;

    /**
     * 会员昵称
     */
    private String nickname;

    /**
     * 会员头像
     */
    private String avatar;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 评论数
     */
    private Integer answerNumber;

    /**
     * 浏览数
     */
    private Integer viewNumber;

    /**
     * 点赞数
     */
    private Integer goodNumber;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}