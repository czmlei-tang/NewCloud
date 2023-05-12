package com.tang.newcloud.service.chat.mapper;

import com.tang.newcloud.service.chat.entity.SendMessage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author 29878
* @description 针对表【send_message】的数据库操作Mapper
* @createDate 2023-02-09 22:22:57
* @Entity com.tang.newcloud.service.chat.entity.SendMessage
*/
public interface SendMessageMapper extends BaseMapper<SendMessage> {

    /**
     * @param friend 好友id
     * @Param id 用户id
     * @return
     */
    SendMessage selectUnreadMessage(String id, String friend);
}




