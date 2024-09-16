package com.example.coverranking.member.presentation;


import com.example.coverranking.member.application.MemberService;
import com.example.coverranking.member.dto.request.AddMemberRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<?> joinUser(@Valid @RequestBody AddMemberRequest addUserRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // 검증 오류 처리
            StringBuilder errors = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> {
                errors.append(error.getDefaultMessage()).append(" ");
            });
            return new ResponseEntity<>(errors.toString().trim(), HttpStatus.BAD_REQUEST);
        }

        try {
            memberService.joinMemberService(addUserRequest);
            return new ResponseEntity<>("회원이 성공적으로 추가되었습니다.", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("서버 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




}
