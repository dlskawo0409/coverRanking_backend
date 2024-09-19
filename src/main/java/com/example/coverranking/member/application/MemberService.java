package com.example.coverranking.member.application;


import com.example.coverranking.common.Image.application.ImageService;
import com.example.coverranking.common.storage.application.S3Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.example.coverranking.member.domain.Blocked;
import com.example.coverranking.member.domain.Member;
import com.example.coverranking.member.domain.MemberRepository;
import com.example.coverranking.member.domain.Role;
import com.example.coverranking.member.dto.request.AddMemberRequest;
import com.example.coverranking.member.exception.MemberErrorCode;
import com.example.coverranking.member.exception.MemberException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.EnumSet;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ImageService imageService;

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
                .preferredGenres(EnumSet.copyOf(addmemberRequest.getPreferredGenre()))
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

}
