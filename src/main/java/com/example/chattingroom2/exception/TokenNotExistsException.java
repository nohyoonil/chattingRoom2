package com.example.chattingroom2.exception;

public class TokenNotExistsException extends RuntimeException{
    public TokenNotExistsException(String message) {
        super(message);
    }
}
