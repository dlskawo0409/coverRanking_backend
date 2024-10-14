package com.example.coverranking.member.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class MemberResponse {
    private Long memberId;
    private String nickName;
    private String imageUrl;
    private Long following;
    private Long follower;

}
