package com.tang.newcloud.service.edu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tang.newcloud.service.edu.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tang.newcloud.service.edu.entity.vo.web.WebCommentQueryVo;
import com.tang.newcloud.service.edu.entity.vo.web.WebCommentVo;

import java.awt.geom.IllegalPathStateException;
import java.util.List;

/**
* @author 29878
* @description 针对表【edu_comment(评论)】的数据库操作Service
* @createDate 2023-01-06 14:07:48
*/
public interface CommentService extends IService<Comment> {

    IPage getCommentList(Long page, Long limit, WebCommentQueryVo webCommentQueryVo);

    Comment getOneComment(Long id);

    List<WebCommentVo> getComments(Long parentId);

    Boolean saveComment(Comment comment);
}
