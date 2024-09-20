package com.example.coverranking.member.domain;

import java.util.List;

public interface MemberRepositoryCustom {
    List<Member> findMembersByNickname(String nickname);
}
