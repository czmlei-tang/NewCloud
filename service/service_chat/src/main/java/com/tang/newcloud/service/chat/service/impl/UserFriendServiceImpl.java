package com.tang.newcloud.service.chat.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tang.newcloud.common.base.result.R;
import com.tang.newcloud.common.base.util.DateUtil;
import com.tang.newcloud.common.base.util.JwtInfo;
import com.tang.newcloud.common.base.util.SnowFlakeUtil;
import com.tang.newcloud.service.base.dto.MemberChatDto;
import com.tang.newcloud.service.chat.entity.FriendRelationship;
import com.tang.newcloud.service.chat.entity.UserFriend;
import com.tang.newcloud.service.chat.entity.vo.FriendVo;
import com.tang.newcloud.service.chat.feign.UcenterService;
import com.tang.newcloud.service.chat.mapper.FriendRelationshipMapper;
import com.tang.newcloud.service.chat.service.UserFriendService;
import com.tang.newcloud.service.chat.mapper.UserFriendMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
* @author 29878
* @description 针对表【user_friend】的数据库操作Service实现
* @createDate 2023-02-09 22:22:57
*/
@Service
public class UserFriendServiceImpl extends ServiceImpl<UserFriendMapper, UserFriend>
    implements UserFriendService{

    @Resource
    private UserFriendMapper userFriendMapper;

    @Autowired
    private UcenterService ucenterService;

    @Resource
    private ThreadPoolTaskExecutor executor;

    @Resource
    private FriendRelationshipMapper friendRelationshipMapper;

    @Resource
    private DataSourceTransactionManager dataSourceTransactionManager;

    @Override
    public int saveFriend(UserFriend userFriend) {
        //查看是否有记录
        String userId = userFriend.getFromId();
        String friendId = userFriend.getToId();
        Integer s = userFriendMapper.selectFriendRequestByFromIdAndToId(userId,friendId);
        if(s!=0){
            return 0;
        }else{
            //无记录
            userFriend.setStatus(0)
                    .setCheckMessage(userFriend.getCheckMessage())
                    .setRemark(userFriend.getRemark())
                    .setGmtCreate(DateUtil.getNow())
                    .setGmtModified(DateUtil.getNow())
                    .setId(String.valueOf(SnowFlakeUtil.getDefaultSnowFlakeId()));
            Integer i = userFriendMapper.insertUserFriend(userFriend);
            return i;
        }
    }

    @Override
    public List<FriendVo> getFriendRequest(String userId) throws ExecutionException, InterruptedException {

        CompletableFuture<List<FriendVo>> future = CompletableFuture.supplyAsync(() -> {
            //我加别人
            List<UserFriend> userFriendList1 = userFriendMapper.selectFriendReponse(userId);
            List<FriendVo> friendVos1 = userFriendList1.stream().map((userFriend -> {
                String toId = userFriend.getToId();
                R r = ucenterService.readNameAndAvatar(toId);
                MemberChatDto member = (MemberChatDto) r.getData().get("member");
                FriendVo friendVo = new FriendVo();
                friendVo.setFriendId(member.getMemberId())
                        .setFriendName(member.getNickname())
                        .setAvatar(member.getAvatar())
                        .setType(1)
                        .setStatus(userFriend.getStatus());
                return friendVo;
            })).collect(Collectors.toList());
            return friendVos1;
        }, executor);

        //别人添加
        List<UserFriend> userFriendList = userFriendMapper.selectFriendRequest(userId);
        List<FriendVo> friendVos = userFriendList.stream().map((userFriend -> {
            String friendId = userFriend.getFromId();
            R r = ucenterService.readNameAndAvatar(friendId);
            MemberChatDto member = (MemberChatDto) r.getData().get("member");
            FriendVo friendVo = new FriendVo();
            friendVo.setFriendId(member.getMemberId())
                    .setFriendName(member.getNickname())
                    .setAvatar(member.getAvatar())
                    .setType(0)
                    .setStatus(userFriend.getStatus());
            return friendVo;
        })).collect(Collectors.toList());


        List<FriendVo> friendVos1 = future.get();
        List<FriendVo> list = new ArrayList<>();
        list.addAll(friendVos);
        list.addAll(friendVos1);

        return list;
    }

    @Override
    public Integer agreeAddFriend(JwtInfo token, String fromId) {
        String toId = token.getId();
        int i = userFriendMapper.updateByFromIdAndToId(fromId,toId,1);
        String remark = userFriendMapper.selectRemarkById(fromId,toId);
        FriendRelationship friendRelationshipOne = new FriendRelationship();
        friendRelationshipOne.setUserId(fromId)
                .setFriendId(toId)
                .setRemark(remark)
                .setStatus(1)
                .setGmtCreate(DateUtil.getNow())
                .setId(String.valueOf(SnowFlakeUtil.getDefaultSnowFlakeId()));

        R r = ucenterService.readNameAndAvatar(fromId);
        MemberChatDto member = (MemberChatDto)r.getData().get("member");
        FriendRelationship friendRelationshipTwo = new FriendRelationship();
        friendRelationshipTwo.setUserId(toId)
                .setFriendId(fromId)
                .setRemark(member.getNickname())
                .setStatus(1)
                .setGmtCreate(DateUtil.getNow())
                .setId(String.valueOf(SnowFlakeUtil.getDefaultSnowFlakeId()));
        List<FriendRelationship> list = new ArrayList<>();
        Integer a = friendRelationshipMapper.insertFriend(list);
        return i+a;
    }

    @Override
    public Integer disagreeFriend(JwtInfo token, String fromId) {
        String toId = token.getId();
        int i = userFriendMapper.updateByFromIdAndToId(fromId,toId,2);
        return i;
    }
}




