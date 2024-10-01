package com.example.coverranking.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);

    @Query(value = """
        SELECT m
        FROM Member m
        WHERE m.nickname like %:nickName%
        """)
    List<Member> findAllMemberByNickname(String nickName);


}
