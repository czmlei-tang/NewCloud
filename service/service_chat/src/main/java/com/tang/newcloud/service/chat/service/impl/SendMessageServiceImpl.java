package com.tang.newcloud.service.chat.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tang.newcloud.service.chat.entity.SendMessage;
import com.tang.newcloud.service.chat.entity.vo.UnreadVo;
import com.tang.newcloud.service.chat.mapper.FriendRelationshipMapper;
import com.tang.newcloud.service.chat.service.SendMessageService;
import com.tang.newcloud.service.chat.mapper.SendMessageMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
* @author 29878
* @description 针对表【send_message】的数据库操作Service实现
* @createDate 2023-02-09 22:22:57
*/
@Service
public class SendMessageServiceImpl extends ServiceImpl<SendMessageMapper, SendMessage>
    implements SendMessageService{

    @Resource
    private SendMessageMapper sendMessageMapper;

    @Resource
    private FriendRelationshipMapper friendRelationshipMapper;

    /**
     *
     * @param id 当前用户id
     * @return
     */
    @Override
    public List<UnreadVo> getUnread(String id) {
        // 查询未读消息，且每个好友一条，并得到为只读总数
        // 查询所有好友id
        List<String> friends = friendRelationshipMapper.selectFriendId(id);
        friends.stream().map(friend -> {
            SendMessage mes = sendMessageMapper.selectUnreadMessage(id, friend);
            return null;
        });
        return null;
    }
}




