package com.tang.newcloud.service.edu.service.impl;

import com.alibaba.excel.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tang.newcloud.common.base.result.R;
import com.tang.newcloud.service.base.dto.CourseDto;
import com.tang.newcloud.service.edu.entity.*;
import com.tang.newcloud.service.edu.entity.form.CourseInfoForm;
import com.tang.newcloud.service.edu.entity.vo.CoursePublishVo;
import com.tang.newcloud.service.edu.entity.vo.CourseQueryVo;
import com.tang.newcloud.service.edu.entity.vo.CourseVo;
import com.tang.newcloud.service.edu.entity.vo.web.WebCourseQueryVo;
import com.tang.newcloud.service.edu.entity.vo.web.WebCourseVo;
import com.tang.newcloud.service.edu.feign.OssFileService;
import com.tang.newcloud.service.edu.mapper.*;
import com.tang.newcloud.service.edu.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tang.newcloud.service.edu.service.VideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author tanglei
 * @since 2022-10-26
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private CourseDescriptionMapper courseDescriptionMapper;

    @Autowired
    private  TeacherMapper teacherMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private OssFileService ossFileService;

    @Autowired
    private CourseCollectMapper courseCollectMapper;

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private ChapterMapper chapterMapper;

    @Autowired
    private VideoService videoService;

    @Autowired
    private CommentMapper commentMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String saveCourseInfo(CourseInfoForm courseInfoForm) {
        //保存课程基本信息
        Course course = new Course();
        course.setStatus(Course.COURSE_DRAFT);
        BeanUtils.copyProperties(courseInfoForm, course);
        baseMapper.insert(course);

        //保存课程详情信息
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setDescription(courseInfoForm.getDescription());
        courseDescription.setId(course.getId());
        courseDescriptionMapper.insert(courseDescription);

        return course.getId();
    }

    @Override
    public IPage<CourseVo> selectPage(Long page, Long limit, CourseQueryVo courseQueryVo) {
        QueryWrapper<CourseVo> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("c.gmt_create");

        String title = courseQueryVo.getTitle();
        String teacherId = courseQueryVo.getTeacherId();
        String subjectParentId = courseQueryVo.getSubjectParentId();
        String subjectId = courseQueryVo.getSubjectId();

        if (!StringUtils.isEmpty(title)) {
            queryWrapper.like("c.title", title);
        }

        if (!StringUtils.isEmpty(teacherId) ) {
            queryWrapper.eq("c.teacher_id", teacherId);
        }

        if (!StringUtils.isEmpty(subjectParentId)) {
            queryWrapper.eq("c.subject_parent_id", subjectParentId);
        }

        if (!StringUtils.isEmpty(subjectId)) {
            queryWrapper.eq("c.subject_id", subjectId);
        }

        Page<CourseVo> pageParam = new Page<>(page, limit);
        //放入分页参数和查询条件参数，mp会自动组装
        List<CourseVo> records = baseMapper.selectPageByCourseQueryVo(pageParam, queryWrapper);
        pageParam.setRecords(records);
        return pageParam;
    }

    @Override
    public CourseInfoForm getCourseInfoById(String id) {
        Course course = baseMapper.selectById(id);
        if (course==null)
            return null;

        CourseDescription courseDescription = courseDescriptionMapper.selectById(id);

        CourseInfoForm courseInfoForm = new CourseInfoForm();
        BeanUtils.copyProperties(course,courseInfoForm);
        courseInfoForm.setDescription(courseDescription.getDescription());

        return courseInfoForm;

    }

    @Override
    @Async("taskExecutor")
    public boolean removeCoverById(String id) {
        Course course = courseMapper.selectById(id);
        if(course != null) {
            String cover = course.getCover();
            if(!StringUtils.isEmpty(cover)){
                //删除图片
                R r = ossFileService.removeFile(cover);
                return r.getSuccess();
            }
        }
        return false;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    @Async(value = "taskExecutor")
    public boolean removeCourseById(String id) {

        //收藏信息：course_collect
        QueryWrapper<CourseCollect> courseCollectQueryWrapper = new QueryWrapper<>();
        courseCollectQueryWrapper.eq("course_id", id);
        courseCollectMapper.delete(courseCollectQueryWrapper);

        //评论信息：comment
        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq("course_id", id);
        commentMapper.delete(commentQueryWrapper);

        //课时信息：video
        QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id", id);
        videoMapper.delete(videoQueryWrapper);

        //章节信息：chapter
        QueryWrapper<Chapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id", id);
        chapterMapper.delete(chapterQueryWrapper);

        //课程详情：course_description
        courseDescriptionMapper.deleteById(id);

        //课程信息：course
        return this.removeById(id);
    }

    @Override
    public String updateCourseInfo(CourseInfoForm courseInfoForm) {
        //保存课程基本信息
        Course course = new Course();
        course.setStatus(Course.COURSE_DRAFT);
        BeanUtils.copyProperties(courseInfoForm, course);
        baseMapper.updateById(course);

        //保存课程详情信息
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setDescription(courseInfoForm.getDescription());
        courseDescription.setId(course.getId());
        courseDescriptionMapper.updateById(courseDescription);

        return course.getId();
    }

    @Override
    public CoursePublishVo getCoursePublishVo(String id) {
        return baseMapper.selectCoursePublishVoById(id);
    }

    @Override
    public boolean publishCourseById(String id) {
        Course course = new Course();
        course.setId(id);
        course.setStatus(Course.COURSE_NORMAL);
        return this.updateById(course);
    }

    @Override
    public List<Course> webSelectList(WebCourseQueryVo webCourseQueryVo) {
        List<Course> courseList=courseMapper.selectByWebCourseQueryVo(webCourseQueryVo);
        return courseList;
    }

    /**
     * 获取课程信息并更新浏览量
     * @param id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public WebCourseVo selectWebCourseVoById(String id) {
        //更新课程浏览数
        Course course = baseMapper.selectById(id);
        course.setViewCount(course.getViewCount() + 1);
        baseMapper.updateById(course);
        //获取课程信息
        WebCourseVo webCourseVo = courseMapper.selectWebCourseVoById(id);
        return webCourseVo;
    }

    @Override
    //这里的value 是该缓存的名称，可以随意写，而key要严格按照查询条件来写，比如这里是查询条件id.
    @Cacheable(value = "index", key = "'selectHotCourse'")
    public List<Course> selectHotCourse() {
        List<Course> courses=courseMapper.selectEightCourse();
        return courses;
    }

    @Override
    public CourseDto getCourseDtoById(String courseId) {
        CourseDto courseDto = new CourseDto();
        //根据课程id，拿到数据包括讲师id
        Course course=courseMapper.selectByCourseId(courseId);
        String teacherId = course.getTeacherId();
        //根据teacherId拿到teacherName
        String teacherName=teacherMapper.selectTeacherNameById(teacherId);
        //集成courseDto属性
        BeanUtils.copyProperties(course,courseDto);
        courseDto.setTeacherName(teacherName);

        return courseDto;
    }

    @Override
    public void updateBuyCountById(String id) {
        Course course = baseMapper.selectById(id);
        course.setBuyCount(course.getBuyCount() + 1);
        this.updateById(course);
    }

    @Override
    @Async("taskExecutor")
    public void removeVodById(String id) {
        List<String> ids=chapterMapper.getChapterById(id);
        for (String i :
                ids) {
            videoService.removeMediaVideosById(i);
        }
    }
}
