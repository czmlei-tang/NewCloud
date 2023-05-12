package com.tang.newcloud.service.edu.entity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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
     * 文章id
     */
//    @TableId
//    private String id;

    /**
     * 文章名
     */
    private String articleName;

    /**
     * 作者
     */
    private String author;

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