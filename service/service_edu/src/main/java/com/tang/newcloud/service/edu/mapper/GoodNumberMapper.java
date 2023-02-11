package com.tang.newcloud.service.edu.mapper;

import com.tang.newcloud.service.edu.entity.GoodNumber;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 29878
* @description 针对表【edu_good_number】的数据库操作Mapper
* @createDate 2023-01-12 16:09:37
* @Entity com.tang.newcloud.service.edu.entity.GoodNumber
*/
public interface GoodNumberMapper extends BaseMapper<GoodNumber> {

    Integer deleteData(GoodNumber goodNumber);
}




