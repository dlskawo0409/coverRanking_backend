package com.example.coverranking.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<?> handleException(Exception e) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
