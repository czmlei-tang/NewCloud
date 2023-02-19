package com.tang.newcloud.service.chat.component.websocket.father;

import com.tang.newcloud.service.chat.component.websocket.ChatInterface;

import javax.websocket.Session;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Tanglei
 * @date 2022-04-25 16:21
 * @description:
 * @version:
 */
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

    private String groupID;

    private String toUserId;



    @Override
    public void onOpen(Session session, @PathParam("key") String key) {
        this.session = session;
        this.userId = userId;
        //加入map
        webSocketMap.put(userId, this);
//        addOnlineCount();           //在线数加1
//        log.info("用户{}连接成功,当前在线人数为{}", userId, getOnlineCount());
//        try {
//            sendMessage(String.valueOf(this.session.getQueryString()));
//        } catch (IOException e) {
//            log.error("IO异常");
//        }
    }


    public void onClose() {

    }


    public void onMessage(String message, Session session) {

    }


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





