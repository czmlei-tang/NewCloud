package com.tang.newcloud.service.edu.service.impl;

import com.tang.newcloud.service.edu.entity.Comment;
import com.tang.newcloud.service.edu.mapper.CommentMapper;
import com.tang.newcloud.service.edu.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author tanglei
 * @since 2022-10-26
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
