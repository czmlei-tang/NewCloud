package com.tang.newcloud.service.edu.controller.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tang.newcloud.common.base.result.R;
import com.tang.newcloud.common.base.util.JwtInfo;
import com.tang.newcloud.common.base.util.JwtUtils;
import com.tang.newcloud.service.edu.entity.Comment;
import com.tang.newcloud.service.edu.entity.vo.TeacherQueryVo;
import com.tang.newcloud.service.edu.entity.vo.web.*;
import com.tang.newcloud.service.edu.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

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
        Map<String,Object> commentList= commentService.getCommentList(page, limit, webCommentQueryVo);
        return R.ok().data(commentList);
    }

    @ApiOperation("根据id获取问答")
    @GetMapping("/get/{id}")
    public R readOneQuestion(@ApiParam(value = "问题id",required = true)@PathVariable Long id,HttpServletRequest request){
        WebCommentIndexVo comment=commentService.getOneComment(id,request);
        List<WebCommentHotVo> hotComment = commentService.getHotComment();
        List<WebCommentTagsVo> list = commentService.getTags();
        WebCommentBestAskVo bestAskVo = commentService.getBestAsk(id,request);
        return R.ok().data("comment",comment).data("hotCommentList",hotComment).data("tags",list).data("bestAskVo",bestAskVo);
    }

    //以修改，只返回一级数据
    @ApiOperation("获取一级评论")
    @GetMapping("/first/{parentId}")
    public R readComments(@ApiParam(value = "父问题（评论）id",required = true)@PathVariable Long parentId,HttpServletRequest request){
        List<WebCommentVo> webCommentVos=commentService.getComments(parentId,request);
        return webCommentVos.size()>0?R.ok().data("webComments",webCommentVos):R.ok().message("还没有人回答，提问者喊你去回答...");
    }

    @ApiOperation("二级数据")
    @GetMapping("/second/{parentId}")
    public R readSecondDataComments(@ApiParam(value = "父问题（评论）id",required = true)@PathVariable Long parentId,HttpServletRequest request){
        List<WebCommentVo> webCommentVos = commentService.getSecondDataComments(parentId,request);
        return webCommentVos.size()>0?R.ok().data("webComments",webCommentVos):R.ok().message("还没有人回答，提问者喊你去回答...");
    }
    /**
     * @description 前端的comment对象必须包括answerid
     * @param comment
     * @param request
     * @return
     */
    @ApiOperation("发布问题或评论")
    @PostMapping("/auth/pulish")
    public R pulishComment(@ApiParam(value = "评论对象",required = true)@RequestBody Comment comment, HttpServletRequest request){
        JwtInfo jwtInfo = JwtUtils.getMemberIdByJwtToken(request);
        if(comment.getContent()==null&&comment.getContent()==""){
            return R.error().message("文本框内容不得为空");
        }
        Boolean isOK = commentService.saveComment(comment,jwtInfo);
        return isOK?R.ok().message("success"):R.error().message("发布失败");
    }

    @ApiOperation("删除评论")
    @DeleteMapping("/auth/delete/{id}")
    public R removeComment(@ApiParam(value = "评论id",required = true) @PathVariable Long id,HttpServletRequest request){
        JwtInfo jwtInfo = JwtUtils.getMemberIdByJwtToken(request);
        String memberId = jwtInfo.getId();
        Boolean isOK = commentService.removeCommentById(id,memberId);
        return isOK?R.ok().message("delete success"):R.error().message("delete error");
    }

    @ApiOperation("改变点赞数")
    @GetMapping("/auth/incr/good-number/{id}")
    public R IncreaseGood(@ApiParam(value = "评论id",required = true)@PathVariable Long id,HttpServletRequest request){
        JwtInfo jwtInfo = JwtUtils.getMemberIdByJwtToken(request);
        String loginMemberId = jwtInfo.getId();
        Boolean isOK = commentService.updateGoodNumber(id,loginMemberId);
        return isOK?R.ok().message("点赞爆棚"):R.error().message("不能重复点赞");
    }


}
