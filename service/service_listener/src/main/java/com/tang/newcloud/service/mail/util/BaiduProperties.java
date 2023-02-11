package com.tang.newcloud.service.mail.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @program: NewCloud
 * @description: 百度ak
 * @author: tanglei
 * @create: 2023-01-10 20:23
 **/
@Data
@Component
//注意prefix要写到最后一个 "." 符号之前
@ConfigurationProperties(prefix="baidu")
public class BaiduProperties {

    private String ak;
}
