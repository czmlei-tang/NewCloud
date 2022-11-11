package com.tang.newcloud.service.edu.entity.vo.web;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 课程分类排序查询
 */
public class WebCourseQueryVo implements Serializable {

    private static final long serialVersionUID = 1L;
    //一级分类
    private String subjectParentId;
    //二级分类
    private String subjectId;

    //销量
    private String buyCountSort;
    //发布时间
    private String gmtCreateSort;
    //价格
    private String priceSort;
    //价格正序：1，价格倒序：2
    private Integer type;
}
