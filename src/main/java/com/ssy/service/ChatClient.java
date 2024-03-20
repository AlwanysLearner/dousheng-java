package com.ssy.service;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Service;

public class ChatClient {
    public static void chat(int producer, int consumer, String msg) {
        // 创建通道和存根
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();
        ChatGrpc.ChatBlockingStub stub = ChatGrpc.newBlockingStub(channel);

        // 构建请求
        ChatProto.ChatRequest request = ChatProto.ChatRequest.newBuilder()
                .setProducter(producer)
                .setConsumer(consumer)
                .setMsg("Hello!")
                .build();

        // 发送请求并接收响应
        ChatProto.ChatResponse response = stub.chat(request);
        System.out.println("Response: " + response.getSuccess());

        // 关闭通道
        channel.shutdown();
    }
}

