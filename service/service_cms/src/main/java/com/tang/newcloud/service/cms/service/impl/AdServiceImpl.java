package com.tang.newcloud.service.cms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tang.newcloud.service.cms.entity.Ad;
import com.tang.newcloud.service.cms.service.AdService;
import com.tang.newcloud.service.cms.mapper.AdMapper;
import org.springframework.stereotype.Service;

/**
* @author 29878
* @description 针对表【cms_ad(广告推荐)】的数据库操作Service实现
* @createDate 2022-11-11 18:50:41
*/
@Service
public class AdServiceImpl extends ServiceImpl<AdMapper, Ad>
    implements AdService{

}




