package com.tang.newcloud.service.chat.service;

import com.tang.newcloud.service.chat.entity.GroupUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tang.newcloud.service.chat.entity.vo.GroupUserVo;

import java.util.List;
import java.util.Map;

/**
* @author 29878
* @description 针对表【group_user】的数据库操作Service
* @createDate 2023-02-09 22:22:57
*/
public interface GroupUserService extends IService<GroupUser> {

    Integer inGroup(String groupId, String userId, String remark);

    /**
     * @description 获取群聊验证
     * @param userId 管理员id
     * @return 元素为groupId,List(该群需要验证的人数)
     */
    List<Map<String, List<GroupUserVo>>> readInMes(String userId);

    Integer savaMaster(String groupId, String userId);

    Integer agreeUser(String id, String adminId);

    Integer disagreeUser(String id, String adminId);

    /**
     *
     * @param groupId
     * @param type 1：自己退群 2.被管理员删除
     * @param memberId 退群用户id
     * @param userId
     * @return
     */
    Integer exitGroup(String groupId, Integer type, String memberId, String userId);
}
