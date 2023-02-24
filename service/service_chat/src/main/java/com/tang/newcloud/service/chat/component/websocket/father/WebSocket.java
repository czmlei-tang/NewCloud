package com.tang.newcloud.service.chat.component.websocket.father;

import com.tang.newcloud.service.chat.inter.ChatInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Tanglei
 * @date 2022-04-25 16:21
 * @description:
 * @version:
 */
@Component
@ServerEndpoint("/auth/websocket/{userId}")
@Slf4j
public class WebSocket implements ChatInterface {


    /**
     * concurrent包的线程安全Map，用来存放每个客户端对应的MyWebSocket对象
     */
    private static ConcurrentHashMap<String,WebSocket> webSocketMap = new ConcurrentHashMap<>();

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 群组id
     */
    private String groupID;

    /**
     * 群发时非空-》群友id，指定好友发时，好友id
     */
    private String friendId;

    /**
     * 1:群发,2:指定好友发送
     */
    private Integer type;

    @OnOpen
    @Override
    public void onOpen(Session session, @PathParam("userId") String userId) {

    }

    @OnClose
    @Override
    public void onClose() {

    }

    @OnMessage
    @Override
    public void onMessage(String message, Session session) {

    }

    @OnError
    @Override
    public void onError(Session session, Throwable error) {

    }

    /**
     * 向客户端发送消息
     */
    public void sendMessage(String message) throws IOException {

    }

    /**
     * 通过userId向客户端发送消息
     */
    public void sendMessageByUserId(String userId, String message) throws IOException {


    }

    /**
     * 群发自定义消息
     */
    public static void sendInfo(String message) throws IOException {

    }

}





