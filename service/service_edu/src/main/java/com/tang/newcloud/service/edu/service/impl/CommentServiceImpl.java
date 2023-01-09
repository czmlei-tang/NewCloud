package com.tang.newcloud.service.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tang.newcloud.service.edu.entity.Comment;
import com.tang.newcloud.service.edu.entity.vo.web.WebCommentQueryVo;
import com.tang.newcloud.service.edu.entity.vo.web.WebCommentVo;
import com.tang.newcloud.service.edu.service.CommentService;
import com.tang.newcloud.service.edu.mapper.CommentMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
* @author 29878
* @description 针对表【edu_comment(评论)】的数据库操作Service实现
* @createDate 2023-01-06 14:07:48
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService{

    @Resource
    private CommentMapper commentMapper;

    @Override
    public IPage getCommentList(Long page, Long limit, WebCommentQueryVo webCommentQueryVo) {
        String courseId = webCommentQueryVo.getCourseId();
        Integer answerNumber = webCommentQueryVo.getAnswerNumber();
        Date gmtCreate = webCommentQueryVo.getGmtCreate();
        Integer watchNumber = webCommentQueryVo.getWatchNumber();

        Page<Comment> commentPage = new Page<>();
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getStatus,1)
                .eq(courseId!=null,Comment::getCourseId,courseId)
                .eq(answerNumber!=null,Comment::getAnswerNumber,0)
                .orderByDesc(watchNumber!=null,Comment::getWatchNumber)
                .orderByDesc(gmtCreate!=null,Comment::getGmtCreate);
        Page<Comment> pageInfo = commentMapper.selectPage(commentPage, queryWrapper);
        return pageInfo;
    }

    @Override
    public Comment getOneComment(Long id) {
        Comment comment = commentMapper.selectOneComment(id);
        commentMapper.updateViewById(id);
        return comment;
    }

    @Override
    public List<WebCommentVo> getComments(Long parentId) {
        List<WebCommentVo> commentVos = commentMapper.selectComments(parentId);
        return commentVos;
    }

    @Override
    public Boolean saveComment(Comment comment) {
        return null;
    }
}




