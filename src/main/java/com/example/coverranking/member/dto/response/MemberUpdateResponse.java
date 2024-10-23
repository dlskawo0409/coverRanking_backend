package com.example.coverranking.member.dto.response;

import com.example.coverranking.member.domain.Genre;

import java.util.List;

public record MemberUpdateResponse(
        String nickName,
        int age,
        String gender,
        List<Genre> preferredGenres,
        String image
) {
    public static MemberUpdateResponse of(String nickName, int age, String gender, List<Genre> preferredGenres, String image){
        return new MemberUpdateResponse(nickName, age, gender,preferredGenres, image);
    }
}