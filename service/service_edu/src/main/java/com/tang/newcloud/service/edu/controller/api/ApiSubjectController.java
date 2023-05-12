package com.tang.newcloud.service.edu.controller.api;

import com.tang.newcloud.common.base.result.R;
import com.tang.newcloud.service.edu.entity.vo.SubjectVo;
import com.tang.newcloud.service.edu.entity.vo.WebSubjectSelectVo;
import com.tang.newcloud.service.edu.service.SubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api("课程")
@RequestMapping("/api/edu/subject")
public class ApiSubjectController {
    @Autowired
    private SubjectService subjectService;

    @ApiOperation("嵌套数据列表")
    @GetMapping("nested-list")
    public R nestedList(){
        List<SubjectVo> subjectVoList = subjectService.nestedList();
        return R.ok().data("items", subjectVoList);
    }

    @ApiOperation("所有类别数据")
    @GetMapping("/like/all")
    public R getLikeSubjectData(@ApiParam(value = "课程查询参数",required = true)String title){
        List<WebSubjectSelectVo> selectVos = subjectService.getLikeSubjectData(title);
        return R.ok().data("tag",selectVos).message("success");
    }
}
