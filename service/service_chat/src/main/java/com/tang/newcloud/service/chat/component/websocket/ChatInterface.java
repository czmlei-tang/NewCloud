package com.tang.newcloud.service.chat.component.websocket;

import javax.websocket.Session;
import javax.websocket.server.PathParam;
import java.io.IOException;

public interface ChatInterface {
    void onOpen(Session session, @PathParam("userId") String userId);
    void onClose();
    void onMessage(String message, Session session);
    void onError(Session session, Throwable error);
    void sendMessage(String message)throws IOException;
    void sendMessageByUserId(String userId, String message)throws IOException;
}
