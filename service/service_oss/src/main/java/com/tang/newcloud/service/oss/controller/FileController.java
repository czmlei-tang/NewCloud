package com.tang.newcloud.service.oss.controller;

import com.tang.newcloud.common.base.result.R;
import com.tang.newcloud.common.base.result.ResultCodeEnum;
import com.tang.newcloud.common.base.util.ExceptionUtils;
import com.tang.newcloud.service.base.exception.NewCloudException;
import com.tang.newcloud.service.oss.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;


@Api(description = "aliyun文件管理")
@RestController
@RequestMapping("/admin/oss/file")
@CrossOrigin
@Slf4j
@RefreshScope
public class FileController {

    @Autowired
    private FileService fileService;

    @ApiOperation("文件上传")
    @PostMapping("/upload")
    public R upload(
            @ApiParam(value = "文件", required = true)
            @RequestParam("file") MultipartFile file,
            @ApiParam(value = "模块", required = true)
            @RequestParam("module") String module)throws IOException {
        String uploadUrl= null;
        InputStream inputStream = file.getInputStream();
        String originalFilename = file.getOriginalFilename();
        try {
            uploadUrl = fileService.upload(inputStream,module,originalFilename);
            return R.ok().message("文件上传成功").data("url", uploadUrl);
        } catch (Exception e) {
            log.error(ExceptionUtils.getMessage(e));
            throw new NewCloudException(ResultCodeEnum.FILE_UPLOAD_ERROR);
        }
    }


    @ApiOperation("文件删除")
    @DeleteMapping("remove")
    public R removeFile(
            @ApiParam(value = "要删除的文件路径", required = true)
            @RequestBody String url) {
        log.info(url);
        fileService.removeFile(url);
        return R.ok().message("文件刪除成功");
    }

    @ApiOperation("测试")
    @GetMapping("/test")
    public R test(){
        return R.ok().message("testB");
    }

}
