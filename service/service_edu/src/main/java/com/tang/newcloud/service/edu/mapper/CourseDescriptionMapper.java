package com.tang.newcloud.service.edu.mapper;

import com.tang.newcloud.service.edu.entity.CourseDescription;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程简介 Mapper 接口
 * </p>
 *
 * @author tanglei
 * @since 2022-10-26
 */

public interface CourseDescriptionMapper extends BaseMapper<CourseDescription> {

    CourseDescription selectDescriptionByCourseId(String id);

}
