package com.example.coverranking.follow.domain;

import com.example.coverranking.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    Follow findByFollowingAndFollower(Member following, Member follower);
    List<Follow> findByFollower(Member follower);
}
