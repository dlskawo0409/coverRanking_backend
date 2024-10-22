package com.example.coverranking.member.dto.response;

public record MemberResponse (
        Long memberId,
     String nickName,
     String imageUrl,
     int following,
     int follower
){
    public static MemberResponse of(Long memberId, String nickName, String imageUrl, int following, int follower){
        return new MemberResponse(memberId, nickName, imageUrl, following, follower);
    }
}
