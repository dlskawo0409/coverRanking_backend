package com.example.coverranking.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existByEmail(String email);
    boolean existByNickname(String nickname);
}
