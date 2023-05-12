package com.tang.newcloud.service.edu.entity.vo.web;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: NewCloud
 * @description:
 * @author: tanglei
 * @create: 2023-02-23 12:20
 **/
@Data
@Accessors(chain = true)
public class WebCommentIndexVo implements Serializable {

    private String id;
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
     * 问题内容
     */
    private String content;

    /**
     * 点赞数
     */
    private Long goodNumber;

    /**
     * 回答数
     */
    private Integer answerNumber;

    /**
     * liulan
     */
    private Integer watchNumber;

    /**
     * 评论时间
     */
    private Date gmtCreate;

    /**
     * 标签
     */
    private String subjectId;

    private String tag;

    /**
     * 子评论
     */
    private String bestAsk;

    /**
     * 点赞记号
     */
    private Integer goodStatus;
}
