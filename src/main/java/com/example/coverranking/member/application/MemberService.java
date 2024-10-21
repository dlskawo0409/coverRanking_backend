package com.example.coverranking.member.application;


import com.example.coverranking.auth.jwt.JWTUtil;
import com.example.coverranking.common.Image.application.ImageService;
import com.example.coverranking.common.Image.domain.Image;
import com.example.coverranking.member.domain.*;
import com.example.coverranking.member.dto.request.CustomMemberDetails;
import com.example.coverranking.member.dto.response.MemberResponse;
import com.example.coverranking.member.dto.response.MemberUpdateResponse;
import org.hibernate.Hibernate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.example.coverranking.member.dto.request.AddMemberRequest;
import com.example.coverranking.member.exception.MemberErrorCode;
import com.example.coverranking.member.exception.MemberException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.coverranking.common.util.CustomBeanUtils.copyNonNullProperties;
import static com.example.coverranking.member.exception.MemberErrorCode.MEMBER_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ImageService imageService;
    private final JWTUtil jwtUtil;


    @Transactional
    public void createMemberService(final AddMemberRequest addmemberRequest, MultipartFile multipartFile){
        String email = addmemberRequest.getEmail();
        String password = addmemberRequest.getPassword();
        String nickname = addmemberRequest.getNickname();
        checkEmailDuplicate(email);
        checkNicknameDuplicate(nickname);

        Image profile = imageService.createImageService(multipartFile);

        memberRepository.save(Member.builder()
                .email(email)
                .password(bCryptPasswordEncoder.encode(password))
                .nickname(nickname)
                .age(addmemberRequest.getAge())
                .gender(String.valueOf(addmemberRequest.getGender()))
                .preferredGenres(addmemberRequest.getPreferredGenre())
                .isBlocked(Blocked.F)
                .role(Role.USER)
                .profile(profile)
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

    public boolean duplicateEmailService(String email){
        try{
            checkEmailDuplicate(email);
        }catch(Exception e){
            return true;
        }
        return false;
    }

    public boolean duplicateNicknameService(String nickName) {
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
        List<MemberResponse> memberResponses = members.stream()
                .filter(member -> member.getDeleted_at() == null)
                .map(member -> {
                    Hibernate.initialize(member.getProfile());
                    return MemberResponse.builder()
                            .memberId(member.getMemberId())
                            .nickName(member.getNickname())
                            .imageUrl(Optional.ofNullable(member.getProfile()).map(Image::getImageUrl).orElse(null))
                            .following(member.getFollowing().stream().count())
                            .follower(member.getFollower().stream().count())
                            .build();
                })
                .collect(Collectors.toList());


        return memberResponses;
    }

    public String updateProfile(CustomMemberDetails loginMember, MultipartFile multipartFile){
        Member member = Optional.ofNullable(loginMember.getMember())
                .orElseThrow(()->new MemberException.MemberConflictException(MEMBER_NOT_FOUND.ILLEGAL_NICKNAME_ALREADY_EXISTS, loginMember.getMember().getEmail()));

        Image profile = imageService.createImageService(multipartFile);
        member.setProfile(profile);
        // s3 이미지 삭제도 다음에 넣도록 하자! lazy 하게 해야 해서 to do로 남겨줌
        return profile.getImageUrl();
    }


    public MemberUpdateResponse updateMember(AddMemberRequest addMemberRequest, MultipartFile multipartFile, CustomMemberDetails loginMember) {
        Member member = Optional.ofNullable(loginMember.getMember())
                .orElseThrow(()->new MemberException.MemberConflictException(MEMBER_NOT_FOUND.ILLEGAL_NICKNAME_ALREADY_EXISTS, loginMember.getMember().getEmail()));

        // Copy non-null properties from addMemberRequest to member
        copyNonNullProperties(addMemberRequest, member);

        // Handle profile image if present
        if (multipartFile != null && !multipartFile.isEmpty()) {
            Image profile = imageService.createImageService(multipartFile);
            member.setProfile(profile);
        }

        // Save the updated member
        Member result = memberRepository.save(member);

        // Build and return the response
        return MemberUpdateResponse.builder()
                .nickName(result.getNickname())
                .age(result.getAge())
                .gender(result.getGender())
                .preferredGenres(result.getPreferredGenres())
                .image(result.getProfile() != null ? result.getProfile().getImageUrl() : null)
                .build();
    }

    public String deleteMember(CustomMemberDetails loginMember) {
        Member member = Optional.ofNullable(loginMember.getMember())
                .orElseThrow(()->new MemberException.MemberConflictException(MEMBER_NOT_FOUND.ILLEGAL_NICKNAME_ALREADY_EXISTS, loginMember.getMember().getEmail()));

        member.setDeleted_at(LocalDateTime.now());
        Member result = memberRepository.save(member);
        return "non-active";
    }

}
