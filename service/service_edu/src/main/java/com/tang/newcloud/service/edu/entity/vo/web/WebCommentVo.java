package com.tang.newcloud.service.edu.entity.vo.web;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: NewCloud
 * @description: 嵌套评论视图
 * @author: tanglei
 * @create: 2023-01-08 21:13
 **/
@Data
public class WebCommentVo {

    @ApiModelProperty(value = "id")
    private String id;
    /**
     * 会员id
     */
    @ApiModelProperty(value = "会员id")
    private String memberId;

    /**
     * 会员昵称
     */
    @ApiModelProperty(value = "会员昵称")
    private String nickname;

    /**
     * 该问题回复的会员名称
     */
    @ApiModelProperty(value = "该问题回复的会员名称")
    private String nameAnswer;

    /**
     * 会员头像
     */
    @ApiModelProperty(value = "会员头像")
    private String avatar;

    /**
     * 点赞
     */
    @ApiModelProperty(value = "点赞")
    private Integer goodNumber;

    /**
     * 回答数
     */
    @ApiModelProperty(value = "回答数")
    private Integer answerNumber;

    /**
     * 问题内容
     */
    @ApiModelProperty(value = "问题内容")
    private String content;

    /**
     * 评论时间
     */
    @ApiModelProperty(value = "评论时间")
    private Date gmtCreate;

    /**
     * 子评论
     */
    @ApiModelProperty(value = "子评论")
    private List<WebCommentVo> children = new ArrayList<>();

}
