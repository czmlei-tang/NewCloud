package com.tang.newcloud.service.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tang.newcloud.service.edu.entity.CourseCollect;
import com.tang.newcloud.service.edu.entity.vo.CourseCollectVo;
import com.tang.newcloud.service.edu.mapper.CourseCollectMapper;
import com.tang.newcloud.service.edu.service.CourseCollectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程收藏 服务实现类
 * </p>
 *
 * @author tanglei
 * @since 2022-10-26
 */
@Service
public class CourseCollectServiceImpl extends ServiceImpl<CourseCollectMapper, CourseCollect> implements CourseCollectService {

    @Resource
    private CourseCollectMapper courseCollectMapper;

    @Override
    public boolean isCollect(String courseId, String memberId) {
        Integer result=courseCollectMapper.selectCourseCollect(courseId,memberId);
        return result>0?true:false;
    }

    @Override
    public void saveCourseCollect(String courseId, String memberId) {
        if(!this.isCollect(courseId, memberId)) {
            CourseCollect courseCollect = new CourseCollect();
            courseCollect.setCourseId(courseId);
            courseCollect.setMemberId(memberId);
            courseCollectMapper.insert(courseCollect);
        }
    }

    @Override
    public List<CourseCollectVo> selectListByMemberId(String memberId) {
        return baseMapper.selectPageByMemberId(memberId);
    }

    @Override
    public boolean removeCourseCollect(String courseId, String memberId) {
        //已收藏则删除
        if(this.isCollect(courseId, memberId)) {
            QueryWrapper<CourseCollect> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("course_id", courseId).eq("member_id", memberId);
            return this.remove(queryWrapper);
        }
        return false;
    }
}
