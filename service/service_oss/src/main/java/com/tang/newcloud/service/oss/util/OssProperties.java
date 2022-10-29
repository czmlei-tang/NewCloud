package com.tang.newcloud.service.oss.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
//注意prefix要写到最后一个 "." 符号之前
@ConfigurationProperties(prefix="aliyun.oss")
public class OssProperties {
    private String endpoint;
    private String keyid;
    private String keysecret;
    private String bucketname;
}