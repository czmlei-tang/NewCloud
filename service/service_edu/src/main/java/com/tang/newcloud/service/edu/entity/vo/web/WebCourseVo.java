package com.tang.newcloud.service.edu.entity.vo.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 课程详情页
 */
public class WebCourseVo {
    private static final long serialVersionUID = 1L;
    //edu_course
    private String id;
    private String title;
    private BigDecimal price;
    private Integer lessonNum;
    private String cover;
    private Long buyCount;
    private Long viewCount;
    //edu_course_description
    private String description;
    //edu_teacher
    private String teacherId;
    private String teacherName;
    private String intro;
    private String avatar;
    //edu_subject
    //二级标题
    private String subjectLevelOneId;
    private String subjectLevelOne;
    //一级标题
    private String subjectLevelTwoId;
    private String subjectLevelTwo;
}
