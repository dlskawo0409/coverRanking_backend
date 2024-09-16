package com.example.coverranking.member.application;


import com.example.coverranking.common.util.RegexUtil;
import com.example.coverranking.member.domain.Blocked;
import com.example.coverranking.member.domain.Member;
import com.example.coverranking.member.domain.MemberRepository;
import com.example.coverranking.member.domain.Role;
import com.example.coverranking.member.dto.request.AddMemberRequest;
import com.example.coverranking.member.exception.MemberErrorCode;
import com.example.coverranking.member.exception.MemberException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.coverranking.member.exception.MemberErrorCode.ILLEGAL_EMAIL_PATTERN;
import static com.example.coverranking.member.exception.MemberErrorCode.ILLEGAL_NICKNAME_LENGTH;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void joinMemberService(final AddMemberRequest addmemberRequest){
        String email = addmemberRequest.getEmail();
        String password = addmemberRequest.getPassword();
        String nickname = addmemberRequest.getNickname();
        checkEmailDuplicate(email);
        checkNicknameDuplicate(nickname);


        memberRepository.save(Member.builder()
                .email(email)
                .password(bCryptPasswordEncoder.encode(password))
                .nickname(nickname)
                .age(addmemberRequest.getAge())
                .gender(String.valueOf(addmemberRequest.getGender()))
                .preferredGenre(addmemberRequest.getPreferredGenre())
                .profile(addmemberRequest.getProfile())
                .isBlocked(Blocked.F)
                .role(Role.USER)
                .build());

    }

    private void checkEmailDuplicate(String email) {
        if (memberRepository.existByEmail(email)) {
            throw new MemberException.MemberConflictException(MemberErrorCode.MEMBER_ALREADY_EXIST, email);
        }
    }

    private void checkNicknameDuplicate(String nickname){
        if(memberRepository.existByNickname(nickname)){
            throw new MemberException.MemberConflictException(MemberErrorCode.ILLEGAL_NICKNAME_ALREADY_EXISTS, nickname);
        }

    }

}
