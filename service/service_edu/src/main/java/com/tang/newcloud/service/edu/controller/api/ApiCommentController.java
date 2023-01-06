package com.tang.newcloud.service.edu.controller.api;

import com.tang.newcloud.common.base.result.R;
import com.tang.newcloud.service.edu.entity.vo.TeacherQueryVo;
import com.tang.newcloud.service.edu.entity.vo.web.WebCommentQueryVo;
import com.tang.newcloud.service.edu.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: NewCloud
 * @description: 问答功能
 * @author: tanglei
 * @create: 2023-01-06 10:57
 **/
@RestController
@RequestMapping("/api/edu/comment")
@Api(description = "问答评论")
@Slf4j
public class ApiCommentController {

    @Autowired
    private CommentService commentService;

    @ApiOperation("get所有问答分页")
    @GetMapping("list/{page}/{limit}")
    public R readAllComment(@ApiParam(value = "当前页码", required = true) @PathVariable Long page,
                            @ApiParam(value = "每页记录数", required = true) @PathVariable Long limit,
                            @ApiParam("问答查询对象")WebCommentQueryVo commentQueryVo){
        List commentList = commentService.getCommentList(page, limit);
        return commentList!=null?R.ok().data("list",commentList):R.error().message("暂无数据");
    }
}
