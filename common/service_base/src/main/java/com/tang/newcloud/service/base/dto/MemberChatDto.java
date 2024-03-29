package com.tang.newcloud.service.base.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @program: NewCloud
 * @description:
 * @author: tanglei
 * @create: 2023-02-13 13:16
 **/
@Data
@Accessors(chain = true)
public class MemberChatDto {
    private static final long serialVersionUID = 1L;
    private String memberId;//会员id
    private String nickname;//昵称
    private String avatar;//头像
}
