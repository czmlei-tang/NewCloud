package com.tang.newcloud.service.edu.mapper;

import com.tang.newcloud.service.edu.entity.Video;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 课程视频 Mapper 接口
 * </p>
 *
 * @author tanglei
 * @since 2022-10-26
 */
public interface VideoMapper extends BaseMapper<Video> {

    List selectVideoSourceIdsByChapterId(@Param("id") String id);

}
