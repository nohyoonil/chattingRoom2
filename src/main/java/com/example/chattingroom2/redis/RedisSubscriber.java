package com.example.chattingroom2.redis;

import com.example.chattingroom2.oauth.model.dto.ChatMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisSubscriber implements MessageListener {

    private final ObjectMapper objectMapper;
    private final SimpMessageSendingOperations messagingTemplate;
    private static final String PRE_URL = "/sub/room/";

    /**
     * Redis에서 메시지가 발행(publish)되면 대기하고 있던 Redis Subscriber가 해당 메시지를 받아 처리
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            // ChatMessage 객채로 맵핑
            log.info("---method onMessage---");
            ChatMessage chatMessage = objectMapper.readValue(message.getBody(), ChatMessage.class);
            messagingTemplate.convertAndSend(PRE_URL + chatMessage.getRoomId(), chatMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
