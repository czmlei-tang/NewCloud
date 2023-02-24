package com.tang.newcloud.service.edu.entity.vo.web;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: NewCloud
 * @description:
 * @author: tanglei
 * @create: 2023-02-24 22:34
 **/
@Data
@Accessors(chain = true)
public class WebCommentBestAskVo implements Serializable {
    private String id;
    private String nickname;
    private String avatar;
    private String answerId;
    private String answerNickName;
    private String content;
    private Integer answerNumber;
    private Integer goodNumber;
    private Date gmtCreate;
}
