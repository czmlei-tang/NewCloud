package com.tang.newcloud.service.edu.service;

import com.tang.newcloud.service.edu.entity.CourseCollect;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tang.newcloud.service.edu.entity.vo.CourseCollectVo;

import java.util.List;

/**
 * <p>
 * 课程收藏 服务类
 * </p>
 *
 * @author tanglei
 * @since 2022-10-26
 */
public interface CourseCollectService extends IService<CourseCollect> {

    boolean isCollect(String courseId, String memberId);

    void saveCourseCollect(String courseId, String memberId);

    List<CourseCollectVo> selectListByMemberId(String id);

    boolean removeCourseCollect(String courseId, String memberId);
}
