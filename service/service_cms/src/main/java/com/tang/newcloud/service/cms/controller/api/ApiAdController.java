package com.tang.newcloud.service.cms.controller.api;

import com.tang.newcloud.common.base.result.R;
import com.tang.newcloud.service.cms.entity.Ad;
import com.tang.newcloud.service.cms.service.AdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(description = "广告推荐")
@RestController
@RequestMapping("/api/cms/ad")
public class ApiAdController {
    @Autowired
    private AdService adService;

    @ApiOperation("根据推荐位id显示广告推荐")
    @GetMapping("list/{adTypeId}")
    public R listByAdTypeId(@ApiParam(value = "推荐位id", required = true) @PathVariable String adTypeId) {

        List<Ad> ads = adService.selectByAdTypeId(adTypeId);
        return R.ok().data("items", ads);
    }
}
