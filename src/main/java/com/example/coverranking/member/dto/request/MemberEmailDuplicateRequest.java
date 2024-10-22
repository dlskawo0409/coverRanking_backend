package com.example.coverranking.member.dto.request;

import lombok.Builder;
import lombok.Getter;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor   // 기본 생성자 추가
@AllArgsConstructor  // 모든 필드를 받는 생성자 추가
public class MemberEmailDuplicateRequest {
    private String email;
}
