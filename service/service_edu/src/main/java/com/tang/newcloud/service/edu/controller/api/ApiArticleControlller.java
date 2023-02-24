package com.tang.newcloud.service.edu.controller.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tang.newcloud.common.base.result.R;
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

import java.util.List;

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
    public R readAllArticle(@ApiParam(value = "当前页码", required = true) @PathVariable Long page,
                            @ApiParam(value = "每页记录数", required = true) @PathVariable Long limit){
        IPage articles = articleService.getArticles(page, limit);
        List records = articles.getRecords();
        long total = articles.getTotal();
        return R.ok().data("records",records).data("totel",total);
    }
}
