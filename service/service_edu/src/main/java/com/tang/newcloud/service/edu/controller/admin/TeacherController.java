package com.tang.newcloud.service.edu.controller.admin;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tang.newcloud.common.base.result.R;
import com.tang.newcloud.service.edu.entity.Teacher;
import com.tang.newcloud.service.edu.entity.vo.TeacherQueryVo;
import com.tang.newcloud.service.edu.service.impl.TeacherServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author tanglei
 * @since 2022-10-26
 */
@Api(description = "讲师管理")
@RestController
@CrossOrigin //跨域
@RequestMapping("/admin/edu/teacher")
@Slf4j
public class TeacherController {

    @Autowired
    private TeacherServiceImpl teacherService;

    /**
     * 得到讲师列表
     * @return
     */
    @ApiOperation("所有讲师列表")
    @GetMapping("/list")
    public R getList(){
        List<Teacher> teacherList=teacherService.getTeacherList();
        return R.ok().data("list",teacherList);
    }

    /**
     * 批量删除讲师
     * @param id
     * @return
     */
    @ApiOperation(value = "根据ID删除讲师", notes = "根据ID删除讲师")
    @DeleteMapping("/remove/{id}")
    public R removeTeacherById(@ApiParam(value = "讲师ID", required = true)@PathVariable String id){
        Integer isRemove = teacherService.removeTeacherById(id);
        return isRemove==1?R.ok().message("删除成功"):R.error().message("数据不存在");
    }

    @GetMapping("list/{page}/{limit}")
    public R listPage(@ApiParam(value = "当前页码", required = true) @PathVariable Long page,
                      @ApiParam(value = "每页记录数", required = true) @PathVariable Long limit,
                      @ApiParam("讲师列表查询对象") TeacherQueryVo teacherQueryVo){

        IPage<Teacher> pageModel = teacherService.selectPage(page, limit, teacherQueryVo);
        List<Teacher> records = pageModel.getRecords();
        long total = pageModel.getTotal();

        return  R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation("新增讲师")
    @PostMapping("/save")
    public R save(@ApiParam(value = "讲师对象", required = true)@RequestBody Teacher teacher){
        log.info(teacher.toString());
        Boolean result = teacherService.save(teacher);
        log.info(result.toString());
        return result?R.ok().message("save ok"):R.error().message("save error");
    }

    @ApiOperation("更新讲师")
    @PutMapping("/update")
    public R updateById(@ApiParam(value = "讲师对象", required = true) @RequestBody Teacher teacher){
        boolean result = teacherService.updateById(teacher);
        return result?R.ok().message("update ok"):R.error().message("update error");
    }

    @ApiOperation("根据id获取讲师信息")
    @GetMapping("get/{id}")
    public R getById(@ApiParam(value = "讲师ID", required = true) @PathVariable String id){
        Teacher teacher = teacherService.getById(id);
        return teacher!=null?R.ok().data("item", teacher):R.error().message("数据不存在");
    }

    @ApiOperation("根据id列表删除讲师")
    @DeleteMapping("/batch-remove")
    public R removeRows(
            @ApiParam(value = "讲师id列表", required = true)
            @RequestBody List<String> idList){
        boolean result = teacherService.removeByIds(idList);
        return result ? R.ok().message("删除成功") : R.error().message("数据不存在");

    }

    @ApiOperation("根据左关键字查询讲师名列表")
    @GetMapping("list/name/{key}")
    public R selectNameListByKey(
            @ApiParam(value = "查询关键字", required = true)
            @PathVariable String key){

        List<Map<String, Object>> nameList = teacherService.selectNameListByKey(key);
        log.info(nameList.toString());
        return R.ok().data("nameList", nameList);
    }

}

