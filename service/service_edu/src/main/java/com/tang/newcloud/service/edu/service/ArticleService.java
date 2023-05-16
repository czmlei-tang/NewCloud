package com.tang.newcloud.service.edu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tang.newcloud.service.edu.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tang.newcloud.service.edu.entity.vo.web.WebArticleIndexHotVo;

import java.util.List;
import java.util.Map;

/**
* @author 29878
* @description 针对表【edu_article】的数据库操作Service
* @createDate 2023-05-09 20:59:25
*/
public interface ArticleService extends IService<Article> {

    Map getArticles(Integer page, Integer limit);

    List<WebArticleIndexHotVo> getHotArticles();

    void increase(String id);
}
