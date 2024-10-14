package com.example.coverranking.follow.domain;

import com.example.coverranking.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    Follow findByFollowingAndFollower(Member following, Member follower);

}
