package com.example.coverranking.member.dto.request;

import com.example.coverranking.member.domain.Gender;
import com.example.coverranking.member.domain.Genre;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class AddMemberRequest {
    @NotBlank(message =  "닉네임은 공백이 될 수 없습니다.")
    @Email(message =  "이메일 형식이 올바르지 않습니다.")
    private final String email;

    @NotBlank(message = "비밀번호는 공백이 될 수 없습니다.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하여야 합니다.")
    @Pattern(
//            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$",
            regexp = "^(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()-+=<>?]).+$",
            message = "비밀번호는 최소 하나의 소문자, 숫자 및 특수문자를 포함해야 합니다."

    )
    private final String password;
    @NotBlank(message = "닉네임은 공백이 될 수 없습니다.")
    private final String nickname;
    private final int age;
    private final Gender gender;
    @NotEmpty(message = "선호 장르는 하나 이상 선택해야 합니다.")
    private final ArrayList<Genre> preferredGenre;

}

