package com.tang.newcloud.service.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tang.newcloud.common.base.result.R;
import com.tang.newcloud.service.cms.entity.Ad;
import com.tang.newcloud.service.cms.entity.vo.AdVo;
import com.tang.newcloud.service.cms.feign.OssFileService;
import com.tang.newcloud.service.cms.service.AdService;
import com.tang.newcloud.service.cms.mapper.AdMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author 29878
* @description 针对表【cms_ad(广告推荐)】的数据库操作Service实现
* @createDate 2022-11-11 18:50:41
*/
@Service
public class AdServiceImpl extends ServiceImpl<AdMapper, Ad>
    implements AdService{

    @Autowired
    private OssFileService ossFileService;

    @Resource
    private AdMapper adMapper;

    @Override
    public IPage<AdVo> selectPage(Long page, Long limit) {

        QueryWrapper<AdVo> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("a.type_id", "a.sort");

        Page<AdVo> pageParam = new Page<>(page, limit);

        List<AdVo> records = adMapper.selectPageByQueryWrapper(pageParam, queryWrapper);
        pageParam.setRecords(records);
        return pageParam;
    }

    @Override
    public boolean removeAdImageById(String id) {
        Ad ad = adMapper.selectById(id);
        if(ad != null) {
            String imagesUrl = ad.getImageUrl();
            if(!StringUtils.isEmpty(imagesUrl)){
                //删除图片
                R r = ossFileService.removeFile(imagesUrl);
                return r.getSuccess();
            }
        }
        return false;
    }

    @Override
    @Cacheable(value = "index", key = "#adTypeId")
    // 这里的value 是该缓存的名称，可以随意写，而key要严格按照查询条件来写，比如这里是查询条件id.
    public List<Ad> selectByAdTypeId(String adTypeId) {
        return adMapper.selectByAdTypeId(adTypeId);
    }
}




