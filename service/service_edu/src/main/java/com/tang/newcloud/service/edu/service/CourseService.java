package com.tang.newcloud.service.edu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tang.newcloud.service.base.dto.CourseDto;
import com.tang.newcloud.service.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tang.newcloud.service.edu.entity.form.CourseInfoForm;
import com.tang.newcloud.service.edu.entity.vo.CoursePublishVo;
import com.tang.newcloud.service.edu.entity.vo.CourseQueryVo;
import com.tang.newcloud.service.edu.entity.vo.CourseVo;
import com.tang.newcloud.service.edu.entity.vo.web.WebCourseQueryVo;
import com.tang.newcloud.service.edu.entity.vo.web.WebCourseVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author tanglei
 * @since 2022-10-26
 */
public interface CourseService extends IService<Course> {

    /**
     * 保存课程和课程详情信息
     * @param courseInfoForm
     * @return 新生成的课程id
     */
    String saveCourseInfo(CourseInfoForm courseInfoForm);

    IPage<CourseVo> selectPage(Long page, Long limit, CourseQueryVo courseQueryVo);

    CourseInfoForm getCourseInfoById(String id);

    boolean removeCoverById(String id);

    boolean removeCourseById(String id);

    String updateCourseInfo(CourseInfoForm courseInfoForm);

    CoursePublishVo getCoursePublishVo(String id);

    boolean publishCourseById(String id);

    List<Course> webSelectList(WebCourseQueryVo webCourseQueryVo);

    /**
     * 获取课程信息并更新浏览量
     * @param id
     * @return
     */
    WebCourseVo selectWebCourseVoById(String id);

    List<Course> selectHotCourse();

    CourseDto getCourseDtoById(String courseId);

    void updateBuyCountById(String id);
}
