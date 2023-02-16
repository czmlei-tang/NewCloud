package com.tang.newcloud.service.chat.entity.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @program: NewCloud
 * @description:
 * @author: tanglei
 * @create: 2023-02-11 22:16
 **/
@Data
@Accessors(chain = true)
public class GroupUserVo {

    //主键id
    private String id;
    //会员id
    private String memberId;
    //申请成员姓名
    private String nickname;
    //申请成员头像
    private String avatar;
    //备注
    private String remark;

    //群id
    private String groupId;
    //群名
    private String groupName;

    //审核用户
    private String verifyUserId;

    //状态
    private Integer auth;



}
