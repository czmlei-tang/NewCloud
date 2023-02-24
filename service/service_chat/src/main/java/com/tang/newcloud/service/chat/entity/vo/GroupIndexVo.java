package com.tang.newcloud.service.chat.entity.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @program: NewCloud
 * @description:
 * @author: tanglei
 * @create: 2023-02-20 20:07
 **/
@Data
@Accessors(chain = true)
public class GroupIndexVo {
    private String groupId;
    private String groupName;
    private Integer total;
    private Integer active;
}
