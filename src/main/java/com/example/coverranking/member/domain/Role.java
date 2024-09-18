package com.example.coverranking.member.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.util.NoSuchElementException;
import java.util.Arrays;
@Getter
public enum Role {
    ADMIN("ROLE_ADMIN", "운영자"),
    USER("ROLE_USER", "로그인 회원"),
    GUEST("ROLE_GUEST", "손님");


    private final String key;
    private final String title;

    Role(String key, String title) {
        this.key = key;
        this.title = title;
    }


    @JsonCreator
    public static Role from(String input) {
        return Arrays.stream(values())
                .filter(role -> isSameName(input, role))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }

    public static boolean isSameName(String input, Role role) {
        return role.name()
                .equalsIgnoreCase(input);
    }

}
