package com.tang.newcloud.service.edu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.tang.newcloud.service.base.model.BaseEntity;
import lombok.Data;

/**
 * 
 * @TableName edu_article_comment
 */
@TableName(value ="edu_article_comment")
@Data
public class ArticleComment extends BaseEntity implements Serializable {

    /**
     * 会员id
     */
    private String memberId;

    /**
     * 文章id
     */
    private String articleId;

    /**
     * 父评论
     */
    private String fatherCommentId;

    /**
     * 点赞数
     */
    private Integer goodNumber;

    /**
     * 是否为首条评论
     */
    private String status;

    /**
     * 评论内容
     */
    private String content;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}