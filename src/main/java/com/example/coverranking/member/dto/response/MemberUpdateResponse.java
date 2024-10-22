package com.example.coverranking.member.dto.response;

import com.example.coverranking.member.domain.Genre;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
public class MemberUpdateResponse {
    private String nickName;
    private int age;
    private String gender;
    private List<Genre> preferredGenres;
    private String image;
}
