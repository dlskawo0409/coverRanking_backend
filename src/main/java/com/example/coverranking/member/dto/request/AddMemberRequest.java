package com.example.coverranking.member.dto.request;

import com.example.coverranking.member.domain.Gender;
import com.example.coverranking.member.domain.Genre;
import jakarta.validation.constraints.*;

import java.util.List;

public record AddMemberRequest(
        @NotBlank(message = "닉네임은 공백이 될 수 없습니다.")
        @Email(message = "이메일 형식이 올바르지 않습니다.")
        String email,

        @NotBlank(message = "비밀번호는 공백이 될 수 없습니다.")
        @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하여야 합니다.")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()-+=<>?]).+$",
                message = "비밀번호는 최소 하나의 소문자, 숫자 및 특수문자를 포함해야 합니다."
        )
        String password,

        @NotBlank(message = "닉네임은 공백이 될 수 없습니다.")
        String nickname,

        int age,

        Gender gender,

        @NotEmpty(message = "선호 장르는 하나 이상 선택해야 합니다.")
        List<Genre> preferredGenre
) {
}
