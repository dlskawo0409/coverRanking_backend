package com.example.coverranking.follow.dto.response;

public record MemberFollowingsResponse(
        Long memberId,
        String nickName,
        String profile
) {
    public static MemberFollowingsResponse of(Long memberId , String nickName, String profile){
        return new MemberFollowingsResponse(memberId, nickName, profile);
    }
}
