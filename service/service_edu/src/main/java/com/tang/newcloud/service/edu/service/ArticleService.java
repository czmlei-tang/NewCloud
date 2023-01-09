package com.tang.newcloud.service.edu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tang.newcloud.service.edu.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 29878
* @description 针对表【edu_article】的数据库操作Service
* @createDate 2023-01-08 12:32:46
*/
public interface ArticleService extends IService<Article> {

    IPage getArticles(Long page, Long limit);
}
