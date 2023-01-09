package com.tang.newcloud.service.edu.entity.vo.web;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @program: NewCloud
 * @description: 网页问答查询对象
 * @author: tanglei
 * @create: 2023-01-06 11:29
 **/
@Data
@Getter
@Setter
public class WebCommentQueryVo {

    /**
     * 课程id
     */
    @ApiModelProperty(value = "课程id")
    private String courseId;


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
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;
}
