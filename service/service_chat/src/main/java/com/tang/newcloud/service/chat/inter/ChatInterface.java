package com.tang.newcloud.service.chat.inter;

import javax.websocket.Session;

public interface ChatInterface {
    void onOpen(Session session,String userId);
    void onClose();
    void onMessage(String message, Session session);
    void onError(Session session, Throwable error);
}
