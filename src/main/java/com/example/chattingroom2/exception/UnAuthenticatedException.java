package com.example.chattingroom2.exception;

import jakarta.servlet.ServletException;

public class UnAuthenticatedException extends ServletException {

    public UnAuthenticatedException(String message) {super(message);}
}
