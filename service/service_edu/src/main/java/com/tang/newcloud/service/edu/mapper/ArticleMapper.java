package com.tang.newcloud.service.edu.mapper;

import com.tang.newcloud.service.edu.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tang.newcloud.service.edu.entity.vo.web.WebArticleIndexHotVo;

import java.util.List;

/**
* @author 29878
* @description 针对表【edu_article】的数据库操作Mapper
* @createDate 2023-05-09 20:59:25
* @Entity com.tang.newcloud.service.edu.entity.Article
*/
public interface ArticleMapper extends BaseMapper<Article> {

    List<WebArticleIndexHotVo> selectHotArticle();

    void updateViewNumber(String id);
}




