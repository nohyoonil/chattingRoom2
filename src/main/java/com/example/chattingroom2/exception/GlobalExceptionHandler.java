package com.example.chattingroom2.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<String> noResourceFoundExceptionHandler(NoResourceFoundException e) {
        return ResponseEntity.badRequest().body("존재하지 않는 요청입니다.");
    }

    @ExceptionHandler(UnvalidatedTokenException.class)
    public ResponseEntity<String> unvalidatedTokenExceptionHandler(UnvalidatedTokenException e) {
        log.error("---unvalidated token---");
        return ResponseEntity.internalServerError().body("토큰이 유효하지 않습니다.");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception e) {
        log.error("---internal server error---");
        return ResponseEntity.internalServerError().body("서버 내부 오류입니다.");
    }
}
