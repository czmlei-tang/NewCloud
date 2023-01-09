package com.tang.newcloud.service.edu.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tang.newcloud.service.edu.entity.ArticleComment;
import com.tang.newcloud.service.edu.service.ArticleCommentService;
import com.tang.newcloud.service.edu.mapper.ArticleCommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author 29878
* @description 针对表【edu_article_comment】的数据库操作Service实现
* @createDate 2023-01-06 15:49:19
*/
@Service
public class ArticleCommentServiceImpl extends ServiceImpl<ArticleCommentMapper, ArticleComment>
    implements ArticleCommentService{

    @Resource
    private ArticleCommentMapper articleCommentMapper;

    @Override
    public List getArticles(Long page, Long limit) {
        return null;
    }
}




