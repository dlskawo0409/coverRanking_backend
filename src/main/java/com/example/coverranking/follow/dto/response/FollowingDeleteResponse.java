package com.example.coverranking.follow.dto.response;

public record FollowingDeleteResponse(
        String unfollowing
){
    public static FollowingDeleteResponse from(String unfollowing){return new FollowingDeleteResponse(unfollowing);}
}
