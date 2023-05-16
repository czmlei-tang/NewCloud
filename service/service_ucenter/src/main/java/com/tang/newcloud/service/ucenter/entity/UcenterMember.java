package com.tang.newcloud.service.ucenter.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import com.tang.newcloud.service.base.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 会员表
 * @TableName ucenter_member
 */
@TableName(value ="ucenter_member")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UcenterMember extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 微信openid
     */
    private String openid;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String mail;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 性别 1 男，2 女
     */
    private Integer sex;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户签名
     */
    private String sign;

    /**
     * 0不在线 1在线
     */
    private Integer status;

    /**
     * 是否禁用 1（true）已禁用，  0（false）未禁用
     */
    private Integer isDisabled;

    /**
     * 逻辑删除 1（true）已删除， 0（false）未删除
     */
    private Integer isDeleted;


}