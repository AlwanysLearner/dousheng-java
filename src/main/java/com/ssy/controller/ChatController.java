package com.ssy.controller;

import com.ssy.POJO.ChatMessage;
import com.ssy.service.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dousheng")
public class ChatController  {
    @PostMapping("/auth/chat/")
    public void chat(@RequestBody ChatMessage chatMessage){
        ChatClient.chat(chatMessage.getUserid1(),chatMessage.getUserid2(),chatMessage.getMessage());
    }
}
