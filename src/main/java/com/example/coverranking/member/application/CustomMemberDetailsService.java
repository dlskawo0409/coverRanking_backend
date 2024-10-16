package com.example.coverranking.member.application;

import com.example.coverranking.member.domain.Member;
import com.example.coverranking.member.domain.MemberRepository;
import com.example.coverranking.member.dto.request.CustomMemberDetails;
import com.example.coverranking.member.exception.MemberException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.coverranking.member.exception.MemberErrorCode.MEMBER_NOT_FOUND;

@Service
public class CustomMemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public CustomMemberDetailsService(MemberRepository memberRepository) {

        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Member userData = Optional.ofNullable(memberRepository.findByEmail(email))
                .orElseThrow(() -> new MemberException.MemberConflictException(MEMBER_NOT_FOUND.MEMBER_NOT_FOUND, email));

        if (userData != null) {
            return new CustomMemberDetails(userData);
        }


        throw new UsernameNotFoundException("User not found with email: " + email);
    }
}
