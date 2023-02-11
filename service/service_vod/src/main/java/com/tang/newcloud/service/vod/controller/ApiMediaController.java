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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(description="阿里云视频点播")
@RestController
@RequestMapping("/api/vod/media")
@Slf4j
public class ApiMediaController {

    @Autowired
    private VideoService videoService;

    @ApiOperation("获取播放凭证")
    @GetMapping("get-play-auth/{videoSourceId}")
    public R getPlayAuth(
            @ApiParam(value = "阿里云视频文件的id", required = true)
            @PathVariable String videoSourceId){

        try{
            String playAuth = videoService.getPlayAuth(videoSourceId);
            return  R.ok().message("获取播放凭证成功").data("playAuth", playAuth);
        } catch (Exception e) {
            log.error(ExceptionUtils.getMessage(e));
            throw new NewCloudException(ResultCodeEnum.FETCH_PLAYAUTH_ERROR);
        }
    }
}