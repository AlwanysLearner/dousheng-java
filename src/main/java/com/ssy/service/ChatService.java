package com.ssy.service;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ChatService {
    private final SimpMessageSendingOperations messagingTemplate;
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    public ChatService(SimpMessageSendingOperations messagingTemplate){
        this.messagingTemplate=messagingTemplate;
    }


    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        // 在这里根据事件添加会话到sessions
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        // 在这里根据事件从sessions移除会话
    }
}
