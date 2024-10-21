package com.example.coverranking.member.dto.request;

import lombok.Builder;
import lombok.Getter;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public record MemberEmailExistRequest(
        String email
) {
}