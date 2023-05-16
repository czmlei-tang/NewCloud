package com.tang.newcloud.service.edu.entity.vo.web;

import lombok.Data;

import java.util.Date;

/**
 * @program: NewCloud
 * @description:
 * @author: tanglei
 * @create: 2023-05-15 18:54
 **/
@Data
public class WebMyAnswerVo {
    private String id;
    private String content;
    private String answerId;
    private Integer goodNumber;
    private Integer answerNumber;
    private Integer watchNumber;
    private Date gmtCreate;
}
