package com.tang.newcloud.service.edu.mapper;

import com.tang.newcloud.service.edu.entity.CourseCollect;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tang.newcloud.service.edu.entity.vo.CourseCollectVo;

import java.util.List;

/**
 * <p>
 * 课程收藏 Mapper 接口
 * </p>
 *
 * @author tanglei
 * @since 2022-10-26
 */
public interface CourseCollectMapper extends BaseMapper<CourseCollect> {

    Integer selectCourseCollect(String courseId, String memberId);

    List<CourseCollectVo> selectPageByMemberId(String memberId);
}
