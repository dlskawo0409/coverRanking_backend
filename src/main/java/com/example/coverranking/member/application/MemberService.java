package com.example.coverranking.member.application;


import com.example.coverranking.common.Image.application.ImageService;
import com.example.coverranking.member.domain.*;
import com.example.coverranking.member.dto.response.MemberResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.example.coverranking.member.dto.request.AddMemberRequest;
import com.example.coverranking.member.exception.MemberErrorCode;
import com.example.coverranking.member.exception.MemberException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ImageService imageService;
    @PersistenceContext
    private EntityManager entityManager;


    @Transactional
    public void createMemberService(final AddMemberRequest addmemberRequest, MultipartFile multipartFile){
        String email = addmemberRequest.getEmail();
        String password = addmemberRequest.getPassword();
        String nickname = addmemberRequest.getNickname();
        checkEmailDuplicate(email);
        checkNicknameDuplicate(nickname);

        imageService.createImageService(multipartFile);

        memberRepository.save(Member.builder()
                .email(email)
                .password(bCryptPasswordEncoder.encode(password))
                .nickname(nickname)
                .age(addmemberRequest.getAge())
                .gender(String.valueOf(addmemberRequest.getGender()))
                .preferredGenres(addmemberRequest.getPreferredGenre())
                .isBlocked(Blocked.F)
                .role(Role.USER)
                .build());

    }

    private void checkEmailDuplicate(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new MemberException.MemberConflictException(MemberErrorCode.MEMBER_ALREADY_EXIST, email);
        }
    }

    private void checkNicknameDuplicate(String nickname){
        if(memberRepository.existsByNickname(nickname)){
            throw new MemberException.MemberConflictException(MemberErrorCode.ILLEGAL_NICKNAME_ALREADY_EXISTS, nickname);
        }

    }

    public boolean DuplicateEmailService(String email){
        try{
            checkEmailDuplicate(email);
        }catch(Exception e){
            return true;
        }
        return false;
    }

    public boolean DuplicateNicknameService(String nickName) {
        try{
            checkNicknameDuplicate(nickName);
        }catch(Exception e){
            return true;
        }
        return false;
    }

    public List<MemberResponse> selectByNickname(String nickname) {
        List<Member> members = memberRepository.findAllMemberByNickname(nickname);

        // 엔티티를 DTO로 변환
        List<MemberResponse> memberResponses = members.stream().map(member -> {
            return MemberResponse.builder()
                    .userId(member.getMemberId())
                    .nickName(member.getNickname())
                    .imageUrl(member.getProfile().getImageUrl())
                    .following(member.getFollowing().stream().count())
                    .follower(member.getFollower().stream().count())
                    .build();

        }).collect(Collectors.toList());

        return memberResponses;
    }

}
