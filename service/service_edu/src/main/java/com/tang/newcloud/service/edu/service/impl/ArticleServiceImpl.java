package com.tang.newcloud.service.edu.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tang.newcloud.service.edu.entity.Article;
import com.tang.newcloud.service.edu.entity.vo.web.WebArticleIndexHotVo;
import com.tang.newcloud.service.edu.entity.vo.web.WebArticleIndexVo;
import com.tang.newcloud.service.edu.service.ArticleService;
import com.tang.newcloud.service.edu.mapper.ArticleMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* @author 29878
* @description 针对表【edu_article】的数据库操作Service实现
* @createDate 2023-05-09 20:59:25
*/
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
    implements ArticleService{

    @Resource
    private ArticleMapper articleMapper;

    @Override
    public Map getArticles(Integer page, Integer limit) {
        Page<Article> articlePage = new Page<>(page, limit);
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewNumber);
        Page<Article> page1 = articleMapper.selectPage(articlePage, queryWrapper);
        List<Article> records = page1.getRecords();
        List<WebArticleIndexVo> collect = records.stream().map(article -> {
            WebArticleIndexVo articleIndexVo = new WebArticleIndexVo();
            BeanUtil.copyProperties(article, articleIndexVo);
            //获取 文章简略
            try {
                articleIndexVo.setContent(this.getArticleContentExpress(article.getContent()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return articleIndexVo;
        }).collect(Collectors.toList());
        long total = page1.getTotal();
        HashMap<String, Object> map = new HashMap<>();
        map.put("article",collect);
        map.put("total",total);
        return map;
    }

    @Override
    public List<WebArticleIndexHotVo> getHotArticles() {
        return articleMapper.selectHotArticle();
    }

    @Override
    public void increase(String id) {
        articleMapper.updateViewNumber(id);
    }

    private String getArticleContentExpress(String content) throws IOException {
        FileReader fileReader = new FileReader(content);
        StringBuffer co = null;
        char[] chars = new char[100];
        int i= fileReader.read(chars);
        if(i!=-1){
            co = new StringBuffer();
            co.append(chars);
            co.append("...");
        }
        return co.toString();
    }
}




