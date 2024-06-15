package com.example.chattingroom2.oauth.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    public enum MessageType {
        ENTER, TALK, EXIT
    }

    private MessageType messageType;
    private String roomId;
    private String sender;
    private String message;
}
