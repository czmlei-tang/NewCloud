package com.tang.newcloud.service.chat.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tang.newcloud.common.base.result.R;
import com.tang.newcloud.service.base.dto.GroupDto;
import com.tang.newcloud.service.base.dto.MemberChatDto;
import com.tang.newcloud.service.chat.entity.GroupUser;
import com.tang.newcloud.service.chat.entity.vo.GroupUserVo;
import com.tang.newcloud.service.chat.feign.UcenterService;
import com.tang.newcloud.service.chat.mapper.ChatGroupMapper;
import com.tang.newcloud.service.chat.service.GroupUserService;
import com.tang.newcloud.service.chat.mapper.GroupUserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
* @author 29878
* @description 针对表【group_user】的数据库操作Service实现
* @createDate 2023-02-09 22:22:57
*/
@Service
public class GroupUserServiceImpl extends ServiceImpl<GroupUserMapper, GroupUser>
    implements GroupUserService{
    @Resource
    private GroupUserMapper groupUserMapper;

    @Resource
    private ChatGroupMapper chatGroupMapper;

    @Resource
    private UcenterService ucenterService;

    @Resource
    private ThreadPoolTaskExecutor executor;

    @Override
    public Integer inGroup(String groupId, String userId, String remark) {
        GroupUser groupUser = new GroupUser();
        groupUser.setMemberId(userId)
        .setGroupId(groupId)
        .setRemark(remark);

        int i = groupUserMapper.insert(groupUser);
        return i;
    }

    @Override
    public List<Map<String, List<GroupUserVo>>> readInMes(String userId) {
        //该用户管理的群
        List<GroupUser> groupUser = groupUserMapper.selectUsersWillIn(userId);
        List<Map<String, List<GroupUserVo>>> list = groupUser.stream().map(groupUser1 -> {

            String groupId = groupUser1.getGroupId();
            //未审核成员
            List<GroupUser> groupUsersNotAck = groupUserMapper.selectUsersNotInThegroupById(groupId);
            GroupDto groupDto = chatGroupMapper.selectGroupNameById(groupId);
            Map<String, List<GroupUserVo>> hashMap = new ConcurrentHashMap<>();

            List<GroupUserVo> groupUserVos = groupUsersNotAck.stream().map(groupUserNotAck -> {
                //创建用户申请vo对象
                GroupUserVo groupUserVo = new GroupUserVo();

                //获取用户信息
                String memberId = groupUserNotAck.getMemberId();
                R r = ucenterService.readNameAndAvatar(memberId);
                MemberChatDto member = (MemberChatDto) r.getData().get("member");
                BeanUtils.copyProperties(member, groupUserVo);
                groupUserVo.setRemark(groupUserNotAck.getRemark())
                        .setAuth(0)
                        .setId(groupUserNotAck.getId());
                BeanUtils.copyProperties(groupDto, groupUserVo);
                //返回用户申请vo对象
                return groupUserVo;
            }).collect(Collectors.toList());
            //返回用户申请vo对象列表
            hashMap.put(groupId, groupUserVos);
            return hashMap;

        }).collect(Collectors.toList());

        return list;
    }

    @Override
    public Integer savaMaster(String groupId,String userId) {
        GroupUser groupUser = new GroupUser();
        groupUser.setGroupId(groupId).setMemberId(userId).setAuth(3);
        int i = groupUserMapper.insert(groupUser);
        return i;
    }

    @Override
    public Integer agreeUser(String id, String adminId) {
        GroupUser groupUser = groupUserMapper.selectUserNotInThegroupById(id);
        if(groupUser==null||groupUser.getAuth()==0){
            return 0;
        }else {
            groupUser.setVerifyUserId(adminId).setAuth(1);
            int i = groupUserMapper.updateById(groupUser);
            return i;
        }
    }

    @Override
    public Integer disagreeUser(String id, String adminId) {
        GroupUser groupUser = groupUserMapper.selectUserNotInThegroupById(id);
        if(groupUser==null||groupUser.getAuth()==0){
            return 1;
        }else {
            groupUser.setVerifyUserId(adminId);
            int i = groupUserMapper.updateById(groupUser);
            return -i;
        }
    }

    @Override
    public Integer exitGroup(String groupId, Integer type, String memberId, String userId) {
        if(type == 1){
            //自己退群
            int i = groupUserMapper.deleteByGroupIdAndUserId(groupId,userId);


        }else{
            //管理员退群
        }
        return null;
    }

}




