package com.tang.newcloud.service.statistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tang.newcloud.common.base.result.R;
import com.tang.newcloud.service.statistics.entity.Daily;
import com.tang.newcloud.service.statistics.feign.UcenterMemberService;
import com.tang.newcloud.service.statistics.service.DailyService;
import com.tang.newcloud.service.statistics.mapper.DailyMapper;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;

/**
* @author 29878
* @description 针对表【statistics_daily(网站统计日数据)】的数据库操作Service实现
* @createDate 2022-11-18 17:11:42
*/
@Service
public class DailyServiceImpl extends ServiceImpl<DailyMapper, Daily>
    implements DailyService{
    @Autowired
    private UcenterMemberService ucenterMemberService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createStatisticsByDay(String day) {
        //如果当日的统计记录已存在，则删除重新统计|或 提示用户当日记录已存在
        QueryWrapper<Daily> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("date_calculated", day);
        baseMapper.delete(queryWrapper);


        //生成统计记录
        R r = ucenterMemberService.countRegisterNum(day);
        Integer registerNum = (Integer)r.getData().get("registerNum");
        int loginNum = RandomUtils.nextInt(100, 200);
        int videoViewNum = RandomUtils.nextInt(100, 200);
        int courseNum = RandomUtils.nextInt(100, 200);

        //在本地数据库创建统计信息
        Daily daily = new Daily();
        daily.setRegisterNum(registerNum);
        daily.setLoginNum(loginNum);
        daily.setVideoViewNum(videoViewNum);
        daily.setCourseNum(courseNum);
        daily.setDateCalculated(day);

        baseMapper.insert(daily);
    }
}




