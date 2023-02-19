package com.tang.newcloud.service.chat.component;

import com.tang.newcloud.service.chat.component.websocket.father.WebSocket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * @program: NewCloud
 * @description: 个人websocket
 * @author: tanglei
 * @create: 2023-02-18 22:36
 **/
@Component
@Slf4j
@ServerEndpoint("/persion/{userId}")
public class PersionalWebsocket extends WebSocket {

    /**
     * 用户id
     */
    private String userId;

    /**
     * 好友id
     */
    private String toUserId;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    @Override
    public void onOpen(Session session, @PathParam("userId") String userId) {

    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    @Override
    public void onClose() {
        super.onClose();
    }

    /**
     * 收到客户端消息后调用的方法
     *{}
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    @Override
    public void onMessage(String message, Session session) {
        super.onMessage(message, session);
    }

    /**
     * 发生错误时调用
     *
     * @OnError
     */
    @OnError
    @Override
    public void onError(Session session, Throwable error) {
        super.onError(session, error);
    }

    @Override
    public void sendMessage(String message) throws IOException {
        super.sendMessage(message);
    }

    @Override
    public void sendMessageByUserId(String userId, String message) throws IOException {
        super.sendMessageByUserId(userId, message);
    }
}
