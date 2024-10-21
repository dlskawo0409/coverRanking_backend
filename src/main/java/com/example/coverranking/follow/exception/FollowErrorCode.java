package com.example.coverranking.follow.exception;

import lombok.Getter;

@Getter
public enum FollowErrorCode {
    ALREADY_FOLLOW("06001", "이미 팔로우 되어있습니다."),
    FOLLOW_SELF("06002", "자기 자신을 팔로우 할 수 없습니다."),
    UNFOLLOW_SELF("06003", "자기 자신을 언팔로우 할 수 없습니다.");
    private final String code;
    private final String message;

    FollowErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
