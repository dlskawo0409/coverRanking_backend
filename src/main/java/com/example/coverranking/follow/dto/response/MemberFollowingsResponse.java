package com.example.coverranking.follow.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberFollowingsResponse {
    private Long memberId;
    private String nickName;
    private String profile;
}
