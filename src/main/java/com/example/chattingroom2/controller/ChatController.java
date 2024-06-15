package com.example.chattingroom2.controller;

import com.example.chattingroom2.oauth.model.dto.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChannelTopic topic;
    private final RedisTemplate redisTemplate;

    @MessageMapping("/chat/message")
    public void message(ChatMessage message) {
        log.info("--message 발송--");
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }
}
