package com.tang.newcloud.service.edu.entity.vo.web;

import lombok.Data;

import java.util.Date;

/**
 * @program: NewCloud
 * @description:
 * @author: tanglei
 * @create: 2023-05-13 13:22
 **/
@Data
public class WebArticleIndexHotVo {
    /**
     * 文章id
     */
    private String id;

    /**
     * 文章名
     */
    private String articleName;

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
