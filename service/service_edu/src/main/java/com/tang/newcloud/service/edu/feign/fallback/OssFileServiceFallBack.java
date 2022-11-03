package com.tang.newcloud.service.edu.feign.fallback;

import com.tang.newcloud.common.base.result.R;
import com.tang.newcloud.service.edu.feign.OssFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class OssFileServiceFallBack implements OssFileService {

    @Override
    public R test() {
        return R.error();
    }

    @Override
    public R removeFile(String url) {
        log.info("熔断保护");
        return R.error();
    }
}