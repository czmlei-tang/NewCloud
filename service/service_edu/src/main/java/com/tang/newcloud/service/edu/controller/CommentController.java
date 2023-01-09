package com.tang.newcloud.service.edu.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tang.newcloud.common.base.result.R;
import com.tang.newcloud.service.edu.entity.Comment;
import com.tang.newcloud.service.edu.entity.vo.web.WebCommentQueryVo;
import com.tang.newcloud.service.edu.mapper.CommentMapper;
import com.tang.newcloud.service.edu.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 * @author tanglei
 * @since 2022-10-26
 */
@RestController
@RequestMapping("admin/edu/comment")
@Api(description = "问答")
public class CommentController {


}

