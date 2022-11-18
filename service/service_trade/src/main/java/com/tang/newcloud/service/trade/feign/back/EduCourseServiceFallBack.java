package com.tang.newcloud.service.trade.feign.back;

import com.tang.newcloud.common.base.result.R;
import com.tang.newcloud.service.base.dto.CourseDto;
import com.tang.newcloud.service.trade.feign.EduCourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EduCourseServiceFallBack implements EduCourseService {
    @Override
    public CourseDto getCourseDtoById(String courseId) {
        log.info("熔断保护");
        return null;
    }

    @Override
    public R updateBuyCountById(String id) {
        log.error("熔断器被执行");
        return R.error();
    }
}
