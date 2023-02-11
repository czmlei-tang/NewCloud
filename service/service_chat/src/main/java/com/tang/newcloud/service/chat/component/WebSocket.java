package com.tang.newcloud.service.chat.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import cn.hutool.core.util.StrUtil;

/**
 * @author Liby
 * @date 2022-04-25 16:21
 * @description:
 * @version:
 */
@ServerEndpoint(value = "/websocket/{userId}")
@Component
@Slf4j
public class WebSocket {

    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的
     */

    private static AtomicInteger onlineCount = new AtomicInteger(0);

    /**
     * concurrent包的线程安全Map，用来存放每个客户端对应的MyWebSocket对象
     */
    private static ConcurrentHashMap<String, WebSocket> webSocketMap = new ConcurrentHashMap<>();

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */

    private Session session;
    /**
     * 用户id
     */
    private String userId;


    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        this.session = session;
        this.userId = userId;
        //加入map
        webSocketMap.put(userId, this);
        addOnlineCount();           //在线数加1
        log.info("用户{}连接成功,当前在线人数为{}", userId, getOnlineCount());
        try {
            sendMessage(String.valueOf(this.session.getQueryString()));
        } catch (IOException e) {
            log.error("IO异常");
        }
    }


    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        //从map中删除
        webSocketMap.remove(userId);
        subOnlineCount();           //在线数减1
        log.info("用户{}关闭连接！当前在线人数为{}", userId, getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *{}
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("来自客户端用户：{} 消息:{}",userId, message);

        //群发消息
        /*for (String item : webSocketMap.keySet()) {
            try {
                webSocketMap.get(item).sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
    }

    /**
     * 发生错误时调用
     *
     * @OnError
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户错误:" + this.userId + ",原因:" + error.getMessage());
        error.printStackTrace();
    }

    /**
     * 向客户端发送消息
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }

    /**
     * 通过userId向客户端发送消息
     */
    public void sendMessageByUserId(String userId, String message) throws IOException {
        log.info("服务端发送消息到{},消息：{}",userId,message);
        if(StrUtil.isNotBlank(userId)&&webSocketMap.containsKey(userId)){
            webSocketMap.get(userId).sendMessage(message);
        }else{
            log.error("用户{}不在线",userId);
        }

    }

    /**
     * 群发自定义消息
     */
    public static void sendInfo(String message) throws IOException {
        for (String item : webSocketMap.keySet()) {
            try {
                webSocketMap.get(item).sendMessage(message);
            } catch (IOException e) {
                continue;
            }
        }
    }

    public static  int getOnlineCount() {
        return onlineCount.get();
    }

    public static  void addOnlineCount() {
        onlineCount.getAndIncrement();
    }

    public static  void subOnlineCount() {
        onlineCount.getAndDecrement();
    }

}





