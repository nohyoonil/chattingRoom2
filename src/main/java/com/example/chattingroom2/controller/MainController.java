package com.example.chattingroom2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MainController {

    @GetMapping("/")
    public String hello() {
        return "HELLO~!";
    }

    @GetMapping("/success")
    public String loginSuccess() {
        return "login success~!";
    }
}
