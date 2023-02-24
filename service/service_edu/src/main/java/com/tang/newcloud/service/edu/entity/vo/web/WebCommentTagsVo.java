package com.tang.newcloud.service.edu.entity.vo.web;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @program: NewCloud
 * @description:
 * @author: tanglei
 * @create: 2023-02-24 20:49
 **/
@Data
@Accessors(chain = true)
public class WebCommentTagsVo implements Serializable {
    private String id;
    private String title;
}
