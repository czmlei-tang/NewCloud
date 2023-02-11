package com.tang.newcloud.service.edu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.tang.newcloud.service.base.model.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 评论
 * @TableName edu_comment
 */
@TableName(value ="edu_comment")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class Comment extends BaseEntity implements Serializable {
    /**
     * 课程id
     */
    @ApiModelProperty(value = "课程id")
    private String courseId;

    /**
     * 讲师id
     */
    @ApiModelProperty(value = "讲师id")
    private String teacherId;

    /**
     * 会员id
     */
    @ApiModelProperty(value = "会员id")
    private String memberId;

    /**
     * 如果不是问题，那回答了哪个问题
     */
    @ApiModelProperty(value = "如果不是问题，那回答了哪个问题")
    private String answerId;

    /**
     * 会员昵称
     */
    @ApiModelProperty(value = "会员昵称")
    private String nickname;

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
     * 浏览数
     */
    @ApiModelProperty(value = "浏览数")
    private Integer watchNumber;

    /**
     * 是否是问题
     */
    @ApiModelProperty(value = "是否是问题")
    private Integer status;

    /**
     * 问题内容
     */
    @ApiModelProperty(value = "问题内容")
    private String content;



    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}