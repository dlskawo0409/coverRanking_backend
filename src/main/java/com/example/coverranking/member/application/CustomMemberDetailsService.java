package com.example.coverranking.member.application;

import com.example.coverranking.member.domain.Member;
import com.example.coverranking.member.domain.MemberRepository;
import com.example.coverranking.member.dto.request.CustomMemberDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomMemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public CustomMemberDetailsService(MemberRepository memberRepository) {

        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Member userData = memberRepository.findByEmail(email);

        if (userData != null) {
            return new CustomMemberDetails(userData);
        }


        throw new UsernameNotFoundException("User not found with email: " + email);
    }
}
