package com.tang.newcloud.service.cms.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tang.newcloud.service.cms.entity.Ad;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tang.newcloud.service.cms.entity.vo.AdVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 29878
* @description 针对表【cms_ad(广告推荐)】的数据库操作Mapper
* @createDate 2022-11-11 18:50:41
* @Entity com.tang.newcloud.service.cms.entity.Ad
*/
public interface AdMapper extends BaseMapper<Ad> {
    List<AdVo> selectPageByQueryWrapper(
            Page<AdVo> pageParam,
            @Param(Constants.WRAPPER) QueryWrapper<AdVo> queryWrapper);

    List<Ad> selectByAdTypeId(String adTypeId);
}




