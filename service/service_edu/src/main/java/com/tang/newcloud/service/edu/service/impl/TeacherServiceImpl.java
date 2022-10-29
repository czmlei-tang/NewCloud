package com.tang.newcloud.service.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tang.newcloud.service.edu.entity.Teacher;
import com.tang.newcloud.service.edu.entity.vo.TeacherQueryVo;
import com.tang.newcloud.service.edu.mapper.TeacherMapper;
import com.tang.newcloud.service.edu.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author tanglei
 * @since 2022-10-26
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Resource
    private TeacherMapper teacherMapper;

    @Override
    public List<Teacher> getTeacherList() {
        return teacherMapper.selectTeacherList();
    }

    @Override
    public Integer removeTeacherById(String id) {
        Integer integer = teacherMapper.deleteTeacherById(id);
        return integer;
    }

    @Override
    public IPage<Teacher> selectPage(Long page, Long limit, TeacherQueryVo teacherQueryVo){
        Page<Teacher> teacherPage = new Page<>(page, limit);

        String name = teacherQueryVo.getName();
        Integer level = teacherQueryVo.getLevel();
        String joinDateBegin = teacherQueryVo.getJoinDateBegin();
        String joinDateEnd = teacherQueryVo.getJoinDateEnd();
        return teacherMapper.selectPage(teacherPage, new LambdaQueryWrapper<Teacher>().orderByAsc(Teacher::getSort)
                .likeRight(StringUtils.isNotEmpty(name), Teacher::getName, name)
                .ge(StringUtils.isNotEmpty(joinDateBegin), Teacher::getJoinDate, joinDateBegin)
                .le(StringUtils.isNotEmpty(joinDateEnd), Teacher::getJoinDate, joinDateEnd)
                .eq(level != null, Teacher::getLevel, level));

    }

    @Override
    public List<Map<String, Object>> selectNameListByKey(String key) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("name");
        queryWrapper.likeRight("name", key);

        List<Map<String, Object>> list = baseMapper.selectMaps(queryWrapper);//返回值是Map列表
        return list;
    }
}
