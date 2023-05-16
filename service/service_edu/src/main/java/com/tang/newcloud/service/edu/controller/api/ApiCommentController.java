package com.tang.newcloud.service.edu.controller.api;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.tang.newcloud.common.base.result.R;
import com.tang.newcloud.common.base.util.JwtInfo;
import com.tang.newcloud.common.base.util.JwtUtils;
import com.tang.newcloud.service.base.dto.MemberChatDto;
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
        Map map = commentService.getCommentDetail(id,request);
        return R.ok().data(map);
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
        if(StrUtil.isEmpty(comment.getContent())){
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
        Integer check = commentService.updateGoodNumber(id,loginMemberId);
        switch (check){
            case 1:
                return R.ok().message("取消点赞");
            case 2:
                return R.ok().message("点赞爆棚");
            default:
                return R.error().message("操作失败");
        }
    }

    @ApiOperation("修改评论头像用户名")
    @PutMapping("/update")
    public R updateComment(@ApiParam(value = "用户信息对象",required = true)@RequestBody MemberChatDto memberChatDto){
        int i = commentService.updateComment(memberChatDto);
        return i>0?R.ok():R.error();
    }

    @ApiOperation("获取我的所有提问")
    @GetMapping("/auth/my/question")
    public R getAllMyQuestions(HttpServletRequest request){
        JwtInfo token = JwtUtils.getMemberIdByJwtToken(request);
        List<WebMyQuestionVo> questions = commentService.getAllMyQuestions(token);
        return R.ok().data("questions",questions);
    }

    @ApiOperation("获取我的所有回复")
    @GetMapping("/auth/my/answer")
    public R getAllMyAnswers(HttpServletRequest request){
        JwtInfo token = JwtUtils.getMemberIdByJwtToken(request);
        List<WebMyAnswerVo> answers = commentService.getAllMyAnswers(token);
        return R.ok().data("answers",answers);
    }


    @ApiOperation("核对状态，是否是问题")
    @GetMapping("/check/status/{id}")
    public R checkStatus(@ApiParam("问题id")@PathVariable String id){
        Boolean b = commentService.checkStatus(id);
        return R.ok().data("status",b);
    }
}
