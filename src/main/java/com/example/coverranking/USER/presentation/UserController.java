package com.example.coverranking.USER.presentation;

import com.example.coverranking.USER.application.UserServie;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    @GetMapping
    public ResponseEntity joinUser(){
        return ResponseEntity.ok().build(); // HTTP 200 OK 상태만 반환
    }



}
