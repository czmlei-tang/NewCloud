package com.tang.newcloud.service.chat.entity.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @program: NewCloud
 * @description:
 * @author: tanglei
 * @create: 2023-02-20 18:11
 **/
@Data
@Accessors(chain = true)
public class FriendListVo {
    private String id;
    private String avatar;
    private String remark;
}
