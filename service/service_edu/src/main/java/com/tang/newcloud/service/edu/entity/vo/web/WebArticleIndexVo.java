package com.tang.newcloud.service.edu.entity.vo.web;

import lombok.Data;

import java.util.Date;

/**
 * @program: NewCloud
 * @description:文章首页
 * @author: tanglei
 * @create: 2023-05-13 11:19
 **/
@Data
public class WebArticleIndexVo {
    /**
     * 文章id
     */
    private String id;

    /**
     * 文章名
     */
    private String articleName;

    /**
     * 文章头像
     */
    private String img;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 评论数
     */
    private Integer answerNumber;

    /**
     * 点赞数
     */
    private Integer goodNumber;

    /**
     * 发布日期
     */
    private Date gmtCreate;
}
