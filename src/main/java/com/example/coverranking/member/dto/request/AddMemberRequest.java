package com.example.coverranking.member.dto.request;

import com.example.coverranking.member.domain.Genre;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
@RequiredArgsConstructor
public class AddMemberRequest {

    @NotBlank(message =  "닉네임은 공백이 될 수 없습니다.")
    private final String email;

    @NotBlank(message = "비밀번호는 공백이 될 수 없습니다.")
    private final String password;
    private final String nickname;
    private final int age;
    private final int gender;
    private final ArrayList<Genre> preferredGenre;
    @NotBlank(message = "프로필 사진은 필수입니다.")
    private final String profile;
}

