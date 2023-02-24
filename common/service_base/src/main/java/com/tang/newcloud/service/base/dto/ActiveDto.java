package com.tang.newcloud.service.base.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @program: NewCloud
 * @description: 用户在线
 * @author: tanglei
 * @create: 2023-02-21 14:10
 **/
@Data
@Accessors(chain = true)
public class ActiveDto {
    private String id;
    private Integer status;
}
