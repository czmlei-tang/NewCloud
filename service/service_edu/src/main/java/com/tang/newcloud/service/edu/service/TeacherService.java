package com.tang.newcloud.service.edu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tang.newcloud.service.edu.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tang.newcloud.service.edu.entity.vo.TeacherQueryVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author tanglei
 * @since 2022-10-26
 */
public interface TeacherService extends IService<Teacher> {

    List<Teacher> getTeacherList();

    Integer removeTeacherById(String id);

    IPage<Teacher> selectPage(Long page, Long limit, TeacherQueryVo teacherQueryVo);

    List<Map<String, Object>> selectNameListByKey(String key);
}
