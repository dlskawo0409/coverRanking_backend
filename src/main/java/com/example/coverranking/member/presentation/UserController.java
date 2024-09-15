package com.example.coverranking.member.presentation;


import com.example.coverranking.member.application.MemberService;
import com.example.coverranking.member.dto.request.AddMemberRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<Void> joinUser(@RequestBody AddMemberRequest addUserRequest) {
        memberService.join(addUserRequest);
        return ResponseEntity.ok().build();
    }



}
