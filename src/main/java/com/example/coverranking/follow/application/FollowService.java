package com.example.coverranking.follow.application;

import com.example.coverranking.auth.jwt.JWTUtil;
import com.example.coverranking.follow.domain.Follow;
import com.example.coverranking.follow.domain.FollowRepository;
import com.example.coverranking.follow.exception.FollowErrorCode;
import com.example.coverranking.follow.exception.FollowException;
import com.example.coverranking.member.domain.Member;
import com.example.coverranking.member.domain.MemberRepository;
import com.example.coverranking.member.exception.MemberException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.coverranking.member.exception.MemberErrorCode.MEMBER_NOT_FOUND;


@Service
@RequiredArgsConstructor
public class FollowService {

    private final JWTUtil jwtUtil;
    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void followByMemberId(Long memberId, String token){
        String email = jwtUtil.getEmail(token);

        Member follower = memberRepository.findByEmail(email);

        if(follower == null){
            throw new MemberException.MemberConflictException(MEMBER_NOT_FOUND.MEMBER_NOT_FOUND, email);
        }

        Member following = memberRepository.findByMemberId(memberId);

        if(following == null){
            throw new MemberException.MemberConflictException(MEMBER_NOT_FOUND.MEMBER_NOT_FOUND, Long.toString(memberId));
        }

        if(followRepository.findByFollowingAndFollower(following, follower) != null){
            throw new FollowException.FollowBadRequestException(FollowErrorCode.ALREADY_FOLLOW);
        }

        followRepository.save(Follow.builder()
                .follower(follower)
                .following(following)
                .build());

    }

}
