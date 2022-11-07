package com.tang.newcloud.service.edu.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
//课时
public class VideoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private Boolean free;
    private Integer sort;

    private String videoSourceId;
}