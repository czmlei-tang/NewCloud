package com.tang.newcloud.service.edu.entity.vo.web;

import lombok.Data;

import java.util.Date;

/**
 * @program: NewCloud
 * @description: 我的所有问题
 * @author: tanglei
 * @create: 2023-05-15 16:43
 **/
@Data
public class WebMyQuestionVo {
    private String id;
    private String content;
    private String subject;
    private Integer goodNumber;
    private Integer answerNumber;
    private Integer watchNumber;
    private Date gmtCreate;
}
