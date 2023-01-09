package com.tang.newcloud.service.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tang.newcloud.service.edu.entity.Article;
import com.tang.newcloud.service.edu.service.ArticleService;
import com.tang.newcloud.service.edu.mapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author 29878
* @description 针对表【edu_article】的数据库操作Service实现
* @createDate 2023-01-08 12:32:46
*/
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
    implements ArticleService{

    @Resource
    private ArticleMapper articleMapper;

    @Override
    public IPage getArticles(Long page, Long limit) {
        Page<Article> articlePage = new Page<>(page,limit);
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewNumber);
        Page<Article> selectPage = articleMapper.selectPage(articlePage, queryWrapper);
        return selectPage;
    }
}




