package com.tang.newcloud.service.edu.controller.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tang.newcloud.common.base.result.R;
import com.tang.newcloud.common.base.util.JwtInfo;
import com.tang.newcloud.common.base.util.JwtUtils;
import com.tang.newcloud.service.edu.entity.Comment;
import com.tang.newcloud.service.edu.entity.vo.TeacherQueryVo;
import com.tang.newcloud.service.edu.entity.vo.web.WebCommentQueryVo;
import com.tang.newcloud.service.edu.entity.vo.web.WebCommentVo;
import com.tang.newcloud.service.edu.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    @ApiOperation("获取问答")
    @GetMapping("/list/{page}/{limit}")
    public R readQuestions(@ApiParam(value = "当前页码", required = true) @PathVariable Long page,
                           @ApiParam(value = "每页记录数", required = true) @PathVariable Long limit,
                           @ApiParam(value ="问答查询对象")WebCommentQueryVo webCommentQueryVo){
        IPage commentList = commentService.getCommentList(page, limit, webCommentQueryVo);
        List commentRecords = commentList.getRecords();
        long commentTotal = commentList.getTotal();
        return R.ok().data("commentRecords",commentRecords).data("commentTotal",commentTotal);
    }

    @ApiOperation("根据id获取问答")
    @GetMapping("/get/{id}")
    public R readOneQuestion(@ApiParam(value = "问题id",required = true)@PathVariable Long id){
        Comment comment=commentService.getOneComment(id);
        return R.ok().data("comment",comment);
    }

    @ApiOperation("获取嵌套评论")
    @GetMapping("/nested-answer/{parentId}")
    public R readComments(@ApiParam(value = "父问题（评论）id",required = true)@PathVariable Long parentId){
        List<WebCommentVo> webCommentVos=commentService.getComments(parentId);
        return R.ok().data("webComments",webCommentVos);
    }

    @ApiOperation("发布问题或评论")
    @PostMapping("/auth/pulish")
    public R pulishComment(@RequestBody Comment comment, HttpServletRequest request){
        JwtInfo jwtInfo = JwtUtils.getMemberIdByJwtToken(request);
        Boolean isOK = commentService.saveComment(comment);
        return isOK?R.ok().message("success"):R.error().message("发布失败");
    }

}
