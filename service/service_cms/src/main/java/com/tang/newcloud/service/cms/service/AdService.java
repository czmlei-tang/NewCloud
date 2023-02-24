package com.tang.newcloud.service.cms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tang.newcloud.service.cms.entity.Ad;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tang.newcloud.service.cms.entity.vo.AdVo;
import io.swagger.models.auth.In;

import java.util.List;

/**
* @author 29878
* @description 针对表【cms_ad(广告推荐)】的数据库操作Service
* @createDate 2022-11-11 18:50:41
*/
public interface AdService extends IService<Ad> {

    IPage<AdVo> selectPage(Long page, Long limit);

    boolean removeAdImageById(String id);

    List<Ad> selectByAdTypeId(String adTypeId);
}
