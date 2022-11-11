package com.tang.newcloud.service.edu.controller.api;

import com.tang.newcloud.common.base.result.R;
import com.tang.newcloud.service.edu.entity.Teacher;
import com.tang.newcloud.service.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@Api(description="讲师")
@RestController
@RequestMapping("/api/edu/teacher")
public class ApiTeacherController {

    @Autowired
    private TeacherService teacherService;

    @ApiOperation(value="所有讲师列表")
    @GetMapping("/list")
    public R listAll(){
        List<Teacher> list = teacherService.list(null);
        return R.ok().data("items", list).message("获取讲师列表成功");
    }

    @ApiOperation(value = "获取讲师")
    @GetMapping("/get/{id}")
    public R get(@PathVariable("id")String id){
        Map<String, Object> map = teacherService.selectTeacherInfoById(id);
        return R.ok().data(map);
    }


}
