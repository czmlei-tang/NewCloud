package com.tang.newcloud.service.edu.controller.api;

import com.tang.newcloud.common.base.result.R;
import com.tang.newcloud.service.edu.entity.Course;
import com.tang.newcloud.service.edu.entity.vo.ChapterVo;
import com.tang.newcloud.service.edu.entity.vo.web.WebCourseQueryVo;
import com.tang.newcloud.service.edu.entity.vo.web.WebCourseVo;
import com.tang.newcloud.service.edu.service.ChapterService;
import com.tang.newcloud.service.edu.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@Api(description="课程")
@RestController
@RequestMapping("/api/edu/course")
public class ApiCourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private ChapterService chapterService;

    @ApiOperation("课程列表")
    @GetMapping("/list")
    public R list(
            @ApiParam(value = "查询对象", required = false)
                    WebCourseQueryVo webCourseQueryVo){
        List<Course> courseList = courseService.webSelectList(webCourseQueryVo);
        return  R.ok().data("courseList", courseList);
    }

    //sql可以优化
    @ApiOperation("根据id查看课程详情")
    @GetMapping("/get/{courseId}")
    public R getWebCourseById(@ApiParam(value = "课程id",required = true)
                              @PathVariable("courseId")String courseId){

        //查询课程信息和讲师信息
        WebCourseVo webCourseVo = courseService.selectWebCourseVoById(courseId);

        //查询当前课程的章节信息
        List<ChapterVo> chapterVoList = chapterService.nestedList(courseId);

        return R.ok().data("course", webCourseVo).data("chapterVoList", chapterVoList);
    }
}