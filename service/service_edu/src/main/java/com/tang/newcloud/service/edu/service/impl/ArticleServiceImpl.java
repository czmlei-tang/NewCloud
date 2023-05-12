package com.tang.newcloud.service.edu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tang.newcloud.service.edu.entity.Article;
import com.tang.newcloud.service.edu.service.ArticleService;
import com.tang.newcloud.service.edu.mapper.ArticleMapper;
import org.springframework.stereotype.Service;

/**
* @author 29878
* @description 针对表【edu_article】的数据库操作Service实现
* @createDate 2023-05-09 20:59:25
*/
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
    implements ArticleService{

}




