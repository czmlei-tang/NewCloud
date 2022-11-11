package com.tang.newcloud.service.edu.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tang.newcloud.service.edu.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tang.newcloud.service.edu.entity.vo.CoursePublishVo;
import com.tang.newcloud.service.edu.entity.vo.CourseVo;
import com.tang.newcloud.service.edu.entity.vo.web.WebCourseQueryVo;
import com.tang.newcloud.service.edu.entity.vo.web.WebCourseVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author tanglei
 * @since 2022-10-26
 */
public interface CourseMapper extends BaseMapper<Course> {

    List<CourseVo> selectPageByCourseQueryVo(
            //mp会自动组装分页参数
            Page<CourseVo> pageParam,
            //mp会自动组装queryWrapper：
            //@Param(Constants.WRAPPER) 和 xml文件中的 ${ew.customSqlSegment} 对应
            @Param(Constants.WRAPPER) QueryWrapper<CourseVo> queryWrapper);

    CoursePublishVo selectCoursePublishVoById(String id);

    List<Course> selectAllByTeacherId(String id);

    List<Course> selectByWebCourseQueryVo(WebCourseQueryVo webCourseQueryVo);

    WebCourseVo selectWebCourseVoById(String courseId);
}
