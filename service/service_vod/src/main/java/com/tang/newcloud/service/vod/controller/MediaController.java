package com.tang.newcloud.service.vod.controller;

import com.tang.newcloud.common.base.result.R;
import com.tang.newcloud.common.base.result.ResultCodeEnum;
import com.tang.newcloud.common.base.util.ExceptionUtils;
import com.tang.newcloud.service.base.exception.NewCloudException;
import com.tang.newcloud.service.vod.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Api(description="阿里云视频点播")
@RestController
@RequestMapping("/admin/vod/media")
@Slf4j
public class MediaController {

    @Autowired
    private VideoService videoService;

    @PostMapping("/upload")
    @ApiOperation("上传音视频")
    public R uploadVideo(
            @ApiParam(name = "file", value = "文件", required = true)
            @RequestParam("file") MultipartFile file) {

        try {
            InputStream inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            String videoId = videoService.uploadVideo(inputStream, originalFilename);
            return R.ok().message("视频上传成功").data("videoId", videoId);
        } catch (IOException e) {
            log.error(ExceptionUtils.getMessage(e));
            throw new NewCloudException(ResultCodeEnum.VIDEO_UPLOAD_TOMCAT_ERROR);
        }

    }


    @DeleteMapping("remove/{vodId}")
    @ApiOperation("根据阿里云视频id删除")
    @RabbitListener(queues = {"newcloud_vod"})
    public R removeVideo(
            @ApiParam(value="阿里云视频id", required = true)
            @PathVariable String vodId){

        try {
            videoService.removeVideo(vodId);
            return R.ok().message("视频删除成功");
        } catch (Exception e) {
            log.error(ExceptionUtils.getMessage(e));
            throw new NewCloudException(ResultCodeEnum.VIDEO_DELETE_ALIYUN_ERROR);
        }
    }

    @DeleteMapping("remove")
    @ApiOperation("根据阿里云视频id列表删除")
    public R removeVideoByIdList(
            @ApiParam(value = "阿里云视频id列表", required = true)
            @RequestBody List<String> videoIdList){

        try {
            videoService.removeVideoByIdList(videoIdList);
            return  R.ok().message("视频删除成功");
        } catch (Exception e) {
            log.error(ExceptionUtils.getMessage(e));
            throw new NewCloudException(ResultCodeEnum.VIDEO_DELETE_ALIYUN_ERROR);
        }
    }
}

