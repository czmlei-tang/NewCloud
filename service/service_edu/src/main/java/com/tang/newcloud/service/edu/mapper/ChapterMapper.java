package com.tang.newcloud.service.edu.mapper;

import com.tang.newcloud.service.edu.entity.Chapter;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author tanglei
 * @since 2022-10-26
 */
public interface ChapterMapper extends BaseMapper<Chapter> {

    List<String> getChapterById(String id);
}
