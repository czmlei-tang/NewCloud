package com.tang.newcloud.service.chat.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tang.newcloud.common.base.result.R;
import com.tang.newcloud.common.base.util.DateUtil;
import com.tang.newcloud.common.base.util.ExceptionUtils;
import com.tang.newcloud.common.base.util.JwtInfo;
import com.tang.newcloud.common.base.util.SnowFlakeUtil;
import com.tang.newcloud.service.base.dto.FriendDto;
import com.tang.newcloud.service.base.dto.MemberChatDto;
import com.tang.newcloud.service.chat.entity.FriendRelationship;
import com.tang.newcloud.service.chat.entity.UserFriend;
import com.tang.newcloud.service.chat.entity.vo.FriendCheckVo;
import com.tang.newcloud.service.chat.entity.vo.FriendListVo;
import com.tang.newcloud.service.chat.feign.UcenterService;
import com.tang.newcloud.service.chat.mapper.FriendRelationshipMapper;
import com.tang.newcloud.service.chat.service.UserFriendService;
import com.tang.newcloud.service.chat.mapper.UserFriendMapper;
import com.tang.newcloud.service.chat.utils.TransactionNameUtils;
import com.tang.newcloud.service.chat.utils.TransactionStatusFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Predicate;
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
    public List<FriendCheckVo> getFriendRequest(String userId) throws ExecutionException, InterruptedException {

        CompletableFuture<List<FriendCheckVo>> future = CompletableFuture.supplyAsync(() -> {
            //我加别人
            List<UserFriend> userFriendList1 = userFriendMapper.selectFriendReponse(userId);
            List<FriendCheckVo> friendCheckVos1 = userFriendList1.stream().map((userFriend -> {
                String toId = userFriend.getToId();
                R r = ucenterService.readNameAndAvatar(toId);
                MemberChatDto member = (MemberChatDto) r.getData().get("member");
                FriendCheckVo friendCheckVo = new FriendCheckVo();
                friendCheckVo.setFriendId(member.getMemberId())
                        .setFriendName(member.getNickname())
                        .setAvatar(member.getAvatar())
                        .setType(1)
                        .setStatus(userFriend.getStatus());
                return friendCheckVo;
            })).collect(Collectors.toList());
            return friendCheckVos1;
        }, executor);

        //别人添加
        List<UserFriend> userFriendList = userFriendMapper.selectFriendRequest(userId);
        List<FriendCheckVo> friendCheckVos = userFriendList.stream().map((userFriend -> {
            String friendId = userFriend.getFromId();
            R r = ucenterService.readNameAndAvatar(friendId);
            MemberChatDto member = (MemberChatDto) r.getData().get("member");
            FriendCheckVo friendCheckVo = new FriendCheckVo();
            friendCheckVo.setFriendId(member.getMemberId())
                    .setFriendName(member.getNickname())
                    .setAvatar(member.getAvatar())
                    .setType(0)
                    .setStatus(userFriend.getStatus());
            return friendCheckVo;
        })).collect(Collectors.toList());


        List<FriendCheckVo> friendCheckVos1 = future.get();
        List<FriendCheckVo> list = new ArrayList<>();
        list.addAll(friendCheckVos);
        list.addAll(friendCheckVos1);

        return list;
    }

    @Override
    public Integer agreeAddFriend(JwtInfo token, String fromId) {
        String toId = token.getId();
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

        TransactionStatus status = new TransactionStatusFactory()
                .getTransactionDefinition(TransactionNameUtils.getTransactionName(), TransactionDefinition.PROPAGATION_REQUIRED);
        try {
            int i = userFriendMapper.updateByFromIdAndToId(fromId, toId, 1);
            Integer a = friendRelationshipMapper.insertFriend(list);
            dataSourceTransactionManager.commit(status);
            return i+a;
        } catch (Exception e) {
            // 错误输入改成日志
            log.error(ExceptionUtils.getMessage(e));
            // 如果发送错误回滚该事物
            dataSourceTransactionManager.rollback(status);
            return 0;
        }
    }

    @Override
    public Integer disagreeFriend(JwtInfo token, String fromId) {
        String toId = token.getId();
        int i = userFriendMapper.updateByFromIdAndToId(fromId,toId,2);
        return i;
    }

    @Override
    public Integer removeFriend(String userId, String friendId) {
        TransactionStatus status = new TransactionStatusFactory()
                .getTransactionDefinition(TransactionNameUtils.getTransactionName(), TransactionDefinition.PROPAGATION_REQUIRED);
        try {
            Integer i = friendRelationshipMapper.deleteByUserIdAndFriendId(userId,friendId);
            Integer a = friendRelationshipMapper.deleteByUserIdAndFriendId(friendId,userId);
            dataSourceTransactionManager.commit(status);
            return i+a;
        } catch (Exception e) {
            // 错误输入改成日志
            log.error(ExceptionUtils.getMessage(e));
            // 如果发送错误回滚该事物
            dataSourceTransactionManager.rollback(status);
            return 0;
        }
    }

    @Override
    public String getFriendRemark(String userId, String friendId) {
        return friendRelationshipMapper.selectRemarkById(userId,friendId);
    }

    @Override
    public List<FriendListVo> getFriendRemarks(String userId) {
        List<FriendRelationship> list = friendRelationshipMapper.selectByUserId(userId);
        List<FriendListVo> friendListVos = list.stream().filter(f -> f.getStatus() == 1).map((m) -> {
            FriendListVo friendListVo = new FriendListVo();
            String friendId = m.getFriendId();
            R r = ucenterService.readFriendAvatar(friendId);
            String avatar = (String) r.getData().get("avatar");
            friendListVo.setId(friendId).setAvatar(avatar).setRemark(m.getRemark());
            return friendListVo;
        }).collect(Collectors.toList());

        return friendListVos;
    }
}




