package com.tang.newcloud.service.edu.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tang.newcloud.common.base.result.R;
import com.tang.newcloud.service.edu.entity.form.CourseInfoForm;
import com.tang.newcloud.service.edu.entity.vo.CoursePublishVo;
import com.tang.newcloud.service.edu.entity.vo.CourseQueryVo;
import com.tang.newcloud.service.edu.entity.vo.CourseVo;
import com.tang.newcloud.service.edu.service.impl.CourseServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author tanglei
 * @since 2022-10-26
 */
@CrossOrigin
@RequestMapping("admin/edu/course")
@RestController
@Api(description = "课程管理")
public class CourseController {
    @Autowired
    private CourseServiceImpl courseService;

    @ApiOperation("分页课程列表")
    @GetMapping("list/{page}/{limit}")
    public R index(
            @ApiParam(value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(value = "查询对象")
                    CourseQueryVo courseQueryVo){

        IPage<CourseVo> pageModel = courseService.selectPage(page, limit, courseQueryVo);
        List<CourseVo> records = pageModel.getRecords();
        long total = pageModel.getTotal();
        return  R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation("新增课程")
    @PostMapping("/save-course-info")
    public R saveCourseInfo(
            @ApiParam(value = "课程基本信息", required = true)
            @RequestBody CourseInfoForm courseInfoForm){
        String courseId = courseService.saveCourseInfo(courseInfoForm);
        return R.ok().data("courseId", courseId).message("保存成功");
    }

    @ApiOperation("根据ID查询课程")
    @GetMapping("/course-info/{id}")
    public R getById(
            @ApiParam(value = "课程ID", required = true)
            @PathVariable String id){

        CourseInfoForm courseInfoForm = courseService.getCourseInfoById(id);
        if (courseInfoForm != null) {
            return R.ok().data("item", courseInfoForm);
        } else {
            return R.error().message("数据不存在");
        }
    }

    @ApiOperation("根据ID删除课程")
    @DeleteMapping("remove/{id}")
    public R removeById(
            @ApiParam(value = "课程ID", required = true)
            @PathVariable String id){

        //TODO 删除视频：VOD
        //在此处调用vod中的删除视频文件的接口

        //删除封面：OSS
        courseService.removeCoverById(id);
        //删除课程
        boolean result = courseService.removeCourseById(id);
        if (result) {
            return R.ok().message("删除成功");
        } else {
            return R.error().message("数据不存在");
        }
    }

    @ApiOperation("根据id修改课程")
    @PutMapping("/update-course-info")
    public R updateCourseInfo(
            @ApiParam(value = "课程基本信息", required = true)
            @RequestBody CourseInfoForm courseInfoForm){
        String courseId = courseService.updateCourseInfo(courseInfoForm);
        return R.ok().data("courseId", courseId).message("保存成功");
    }

    @ApiOperation("根据ID获取课程发布信息")
    @GetMapping("course-publish/{id}")
    public R getCoursePublishVoById(
            @ApiParam(value = "课程ID", required = true)
            @PathVariable String id) {
        CoursePublishVo coursePublishVo=courseService.getCoursePublishVo(id);

        if(coursePublishVo!=null){
            return R.ok().data("item", coursePublishVo);
        }

        return R.error().message("数据不存在");
    }

    @ApiOperation("根据id发布课程")
    @PutMapping("publish-course/{id}")
    public R publishCourseById(
            @ApiParam(value = "课程ID", required = true)
            @PathVariable String id){

        boolean result = courseService.publishCourseById(id);
        if (result) {
            return R.ok().message("发布成功");
        } else {
            return R.error().message("数据不存在");
        }
    }
}

