package com.tang.newcloud.service.base.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @program: NewCloud
 * @description: 朋友详情
 * @author: tanglei
 * @create: 2023-02-20 15:09
 **/
@Data
@Accessors(chain = true)
public class FriendDto {
    /**
     * 会员id
     */
    private String memberId;
    /**
     * 备注
     */
    private String remark;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 微信号
     */
    private String openid;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 手机号
     */
    private String mobile;
}
