package com.tang.newcloud.service.vod.service;

import com.aliyuncs.exceptions.ClientException;

import java.io.InputStream;
import java.util.List;

public interface VideoService {
    String uploadVideo(InputStream file, String originalFilename);

    void removeVideo(String videoId) throws ClientException;

    void removeVideoByIdList(List<String> videoIdList)throws ClientException;

    String getPlayAuth(String videoSourceId)throws ClientException;
}