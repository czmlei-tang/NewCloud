package com.tang.newcloud.service.statistics.service;

import com.tang.newcloud.service.statistics.entity.Daily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
* @author 29878
* @description 针对表【statistics_daily(网站统计日数据)】的数据库操作Service
* @createDate 2022-11-18 17:11:42
*/
public interface DailyService extends IService<Daily> {

    void createStatisticsByDay(String day);

    Map<String, Map<String, Object>> getChartData(String begin, String end);
}
