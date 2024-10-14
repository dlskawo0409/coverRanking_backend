package com.example.coverranking.follow.dto.response;

import lombok.Builder;

@Builder
public class MemberFollowingsResponse {
    private Long memberId;
    private String nickName;
    private String profile;
}
