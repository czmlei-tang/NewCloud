package com.tang.newcloud.service.edu.service;

import com.tang.newcloud.service.edu.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author tanglei
 * @since 2022-10-26
 */
public interface CommentService extends IService<Comment> {

    List getCommentList(Long page, Long limit);
}
