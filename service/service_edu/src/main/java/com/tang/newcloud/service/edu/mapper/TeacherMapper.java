package com.tang.newcloud.service.edu.mapper;

import com.tang.newcloud.service.edu.entity.Teacher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 讲师 Mapper 接口
 * </p>
 *
 * @author tanglei
 * @since 2022-10-26
 */
public interface TeacherMapper extends BaseMapper<Teacher> {

    List<Teacher> selectTeacherList();

    Integer deleteTeacherById(String ids);
}
