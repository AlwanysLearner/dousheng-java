package com.ssy.controller;

import com.ssy.POJO.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController  {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/auth/message")
    @SendTo("/topic/messages")
    public ChatMessage sendMessage(ChatMessage message) {
        return message; // 将消息发送给订阅了`/topic/messages`的所有用户
        //消息存储
    }

    @MessageMapping("/auth/message/user")
    public void sendMessageToUser(@Payload ChatMessage message) {
        messagingTemplate.convertAndSendToUser(String.valueOf(message.getUserid2()), "/queue/reply",String.valueOf(message.getUserid1())+ message);
        //消息存储
    }
}
