package com.tang.newcloud.service.edu.service;

import com.tang.newcloud.service.edu.entity.ArticleComment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 29878
* @description 针对表【edu_article_comment】的数据库操作Service
* @createDate 2023-01-06 15:49:19
*/
public interface ArticleCommentService extends IService<ArticleComment> {

    List getArticles(Long page, Long limit);
}
