package com.tang.newcloud.service.chat.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tang.newcloud.service.chat.entity.SendMessage;
import com.tang.newcloud.service.chat.service.SendMessageService;
import com.tang.newcloud.service.chat.mapper.SendMessageMapper;
import org.springframework.stereotype.Service;

/**
* @author 29878
* @description 针对表【send_message】的数据库操作Service实现
* @createDate 2023-02-09 22:22:57
*/
@Service
public class SendMessageServiceImpl extends ServiceImpl<SendMessageMapper, SendMessage>
    implements SendMessageService{

}




