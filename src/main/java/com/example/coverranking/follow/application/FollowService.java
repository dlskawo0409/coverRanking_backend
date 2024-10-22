package com.example.coverranking.follow.application;

import com.example.coverranking.auth.jwt.JWTUtil;
import com.example.coverranking.common.storage.application.S3Service;
import com.example.coverranking.follow.domain.Follow;
import com.example.coverranking.follow.domain.FollowRepository;
import com.example.coverranking.follow.dto.response.MemberFollowingsResponse;
import com.example.coverranking.follow.exception.FollowErrorCode;
import com.example.coverranking.follow.exception.FollowException;
import com.example.coverranking.member.domain.Member;
import com.example.coverranking.member.domain.MemberRepository;
import com.example.coverranking.member.dto.request.CustomMemberDetails;
import com.example.coverranking.member.exception.MemberException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.coverranking.member.exception.MemberErrorCode.MEMBER_NOT_FOUND;


@Service
@RequiredArgsConstructor
public class FollowService {

    private final JWTUtil jwtUtil;
    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;
    private final S3Service s3Service;

    @Transactional
    public void followingByMemberId(CustomMemberDetails loginMember, Long memberId){
        String email = loginMember.getMember().getEmail();

        Member follower = Optional.ofNullable(memberRepository.findByEmail(email))
                .orElseThrow(() -> new MemberException.MemberConflictException(MEMBER_NOT_FOUND.MEMBER_NOT_FOUND, email));

        Member following = Optional.ofNullable(memberRepository.findByMemberId(memberId))
                .orElseThrow(()-> new MemberException.MemberConflictException(MEMBER_NOT_FOUND.MEMBER_NOT_FOUND, Long.toString(memberId)));

        if(follower.equals(following)){
            throw new FollowException.FollowBadRequestException(FollowErrorCode.FOLLOW_SELF);
        }

        if(followRepository.findByFollowingAndFollower(following, follower) != null){
            throw new FollowException.FollowBadRequestException(FollowErrorCode.ALREADY_FOLLOW);
        }

        followRepository.save(Follow.builder()
                .follower(follower)
                .following(following)
                .build());

    }

    public List<MemberFollowingsResponse> getFollowingsByNickName(String nickname) {

        Member follower = Optional.ofNullable(memberRepository.findOneMemberByNickname(nickname))
                .orElseThrow(() -> new MemberException.MemberConflictException(MEMBER_NOT_FOUND.MEMBER_NOT_FOUND, nickname));

        List<Follow> follows =  followRepository.findByFollower(follower);
        List<MemberFollowingsResponse> result = follows.stream()
                .map(follow -> MemberFollowingsResponse.of(
                                follow.getFollowing().getMemberId(),
                                follow.getFollowing().getNickname(),
                                s3Service.getPresignedURL(follow.getFollowing().getProfile().getImageUrl())
                        ))
                .collect(Collectors.toList());


        return result;
    }

    // 영속성을 유지해야 삭제할 수 있다.
    @Transactional
    public String deleteFollowing(CustomMemberDetails loginMember, Long memberId){

        Member follower = Optional.ofNullable(memberRepository.findByEmail(loginMember.getMember().getEmail()))
                .orElseThrow(() -> new MemberException.MemberConflictException(MEMBER_NOT_FOUND.MEMBER_NOT_FOUND, loginMember.getMember().getEmail()));


        Member following = Optional.ofNullable(memberRepository.findByMemberId(memberId))
                .orElseThrow(() -> new MemberException.MemberConflictException(MEMBER_NOT_FOUND.MEMBER_NOT_FOUND, String.valueOf(memberId)));

        if(follower.getEmail().equals(following.getEmail())){
            throw  new FollowException.FollowBadRequestException(FollowErrorCode.UNFOLLOW_SELF);
        }

        followRepository.deleteByFollowerAndFollowing(follower, following);
        return following.getNickname();
    }

}
