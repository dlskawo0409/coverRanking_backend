package com.example.coverranking.member.domain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    private final JdbcTemplate jdbcTemplate;

    private final JpaQueryFactory jpaQueryFactory;

    @Override
    public List<Member> findMembersByNickname(String nickname) {
        String jpql = "SELECT m FROM Member m WHERE m.nickname LIKE :nickname";
        return entityManager.createQuery(jpql, Member.class)
                .setParameter("nickname", "%" + nickname + "%")
                .getResultList();
    }
}
