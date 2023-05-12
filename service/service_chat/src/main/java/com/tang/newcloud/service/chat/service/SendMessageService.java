package com.tang.newcloud.service.chat.service;

import com.tang.newcloud.service.chat.entity.SendMessage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tang.newcloud.service.chat.entity.vo.UnreadVo;

import java.util.List;

/**
* @author 29878
* @description 针对表【send_message】的数据库操作Service
* @createDate 2023-02-09 22:22:57
*/
public interface SendMessageService extends IService<SendMessage> {

    List<UnreadVo> getUnread(String id);
}
