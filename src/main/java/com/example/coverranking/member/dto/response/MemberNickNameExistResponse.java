package com.example.coverranking.member.dto.response;

public record MemberNickNameExistResponse(
        boolean isExist
) {

    public MemberNickNameExistResponse(boolean isExist) {
        this.isExist = isExist;
    }

    public static boolean MemberNickNameExistResponse(boolean isExist) {
        return MemberNickNameExistResponse(isExist);
    }
}
