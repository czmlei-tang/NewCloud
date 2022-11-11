package com.tang.newcloud.service.edu.service.impl;

import com.tang.newcloud.service.edu.entity.Video;
import com.tang.newcloud.service.edu.feign.VodMediaService;
import com.tang.newcloud.service.edu.mapper.VideoMapper;
import com.tang.newcloud.service.edu.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author tanglei
 * @since 2022-10-26
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    @Autowired
    private VodMediaService vodMediaService;

    @Autowired
    private VideoMapper videoMapper;

    @Override
    public void  removeMediaVideoById(String id) {

        log.warn("VideoServiceImpl：video id = " + id);
        //根据课时id找到视频id
        Video video = baseMapper.selectById(id);
        String videoSourceId = video.getVideoSourceId();
        log.warn("VideoServiceImpl：videoSourceId= " + videoSourceId);
        vodMediaService.removeVideo(videoSourceId);
    }

    @Override
    public void removeMediaVideosById(String id) {
        List<Object> videoSourceIds=videoMapper.selectVideoSourceIdsByChapterId(id);
        List<String> videoSourceIds2 = new ArrayList<>();
        for (Object s :
                videoSourceIds) {
            videoSourceIds2.add((String) s);
        }

        vodMediaService.removeVideoByIdList(videoSourceIds2);
    }
}
