package com.example.coverranking.member.dto.response;

public record MemberDeleteResponse(
        String userState
) {
    public static MemberDeleteResponse  from(String userState){ return new MemberDeleteResponse(userState);}
}
