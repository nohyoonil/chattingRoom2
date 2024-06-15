package com.example.chattingroom2.controller;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TestController {

    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> valueOperation;

    @GetMapping("/")
    public String hello() {
        return "HELLO~!";
    }

    @GetMapping("/success")
    public String loginSuccess() {
        return "login success~!";
    }

    @PostMapping("/redis") // for test
    public String redisInsertTest(@RequestParam String key, @RequestParam String value) {
        valueOperation.set(key, value);
        return "insert success";
    }

    @GetMapping("/redis/{key}") // for test
    public ResponseEntity<String> redisGetTest(@PathVariable String key) {
        String value = valueOperation.get(key);
        return ResponseEntity.ok(key + " : " + value);
    }
}
