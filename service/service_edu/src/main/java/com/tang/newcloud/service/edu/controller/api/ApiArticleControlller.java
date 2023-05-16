package com.tang.newcloud.service.edu.controller.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tang.newcloud.common.base.result.R;
import com.tang.newcloud.common.base.result.ResultCodeEnum;
import com.tang.newcloud.service.base.exception.NewCloudException;
import com.tang.newcloud.service.edu.entity.Article;
import com.tang.newcloud.service.edu.entity.vo.web.WebArticleIndexHotVo;
import com.tang.newcloud.service.edu.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileReader;
import java.util.List;
import java.util.Map;

/**
 * @program: NewCloud
 * @description: web文章
 * @author: tanglei
 * @create: 2023-01-09 14:40
 **/
@RestController
@RequestMapping("/api/edu/article")
@Api(description = "web文章")
@Slf4j
public class ApiArticleControlller {
    @Autowired
    private ArticleService articleService;

    @ApiOperation("获取分页文章")
    @GetMapping("list/{page}/{limit}")
    public R readAllArticle(@ApiParam(value = "当前页码", required = true) @PathVariable Integer page,
                            @ApiParam(value = "每页记录数", required = true) @PathVariable Integer limit){
        Map map = articleService.getArticles(page, limit);
        return R.ok().data(map);
    }

    @ApiOperation("获取热门文章")
    @GetMapping("hot")
    public R readHotArticles() {
        List<WebArticleIndexHotVo> hotVoList = articleService.getHotArticles();
        return R.ok().data("hotArticle",hotVoList);
    }

    @ApiOperation("获取文章")
    @GetMapping("read/{id}")
    public R readArticle(@PathVariable String id) {
        Article article = articleService.getById(id);
        StringBuffer co = null;
        try {
            FileReader fileReader = new FileReader(article.getContent());
            int i =0;
            char[] chars = new char[100];
            co = new StringBuffer();
            while ((i = (fileReader.read(chars)))!=-1){
                co.append(chars);
            }
            article.setContent(co.toString());
            articleService.increase(id);
        }catch (Exception e){
            throw new NewCloudException(ResultCodeEnum.ERROR_READING_FILE);
        }
        return R.ok().data("article",article);
    }
}
