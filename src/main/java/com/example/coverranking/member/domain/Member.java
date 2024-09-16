package com.example.coverranking.member.domain;

import com.example.coverranking.comment.BasicEntity;
import com.example.coverranking.common.util.RegexUtil;
import com.example.coverranking.member.exception.MemberException.*;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import static com.example.coverranking.member.exception.MemberErrorCode.*;

@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DTYPE")
public class Member extends BasicEntity implements UserDetails  {

    private static final String VALID_EMAIL_URL_REGEX = "^[a-zA-Z0-9]+@[a-zA-Z]+\\.[a-zA-Z]{2,}$";
    private static final int NICKNAME_LENGTH = 20;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID", updatable = false)
    private Long id;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "PASSWORD",  nullable = false)
    private String password;

    @Column(name = "NICKNAME", length = 20, unique = true)
    private String nickname;

    @Column(name = "AGE", nullable = false)
    private int age;

    @Column(name = "GENDER")
    private String gender;

    @Column(name = "PREFERRED_GENRE")
    private ArrayList<Genre> preferredGenre;

    @Column(name = "PROFILE")
    private String profile;

    @Column(name = "IS_BLOCKED")
    private Blocked isBlocked;

    @Column(name="ROLE", nullable = false)
    private Role role;

    // 생성자에서 @Builder 제거
    public Member(String email, String password, String nickname, Role role) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    private static void validateNickName(String nickName) throws MemberBadRequestException {
        if (Objects.isNull(nickName)) {
            throw new MemberBadRequestException(ILLEGAL_NICKNAME_NULL);
        }
        if (nickName.isBlank() || nickName.length() > NICKNAME_LENGTH) {
            throw new MemberBadRequestException(ILLEGAL_NICKNAME_LENGTH);
        }
    }

    private static void validateEmail(String email) throws MemberBadRequestException {
        if (Objects.isNull(email)) {
            throw new MemberBadRequestException(ILLEGAL_EMAIL_NULL);
        }

        if (!RegexUtil.matches(VALID_EMAIL_URL_REGEX, email)) {
            throw new MemberBadRequestException(ILLEGAL_EMAIL_PATTERN);
        }
    }

    private static void validateRole(Role role) {
        if (Objects.isNull(role)) {
            throw new IllegalArgumentException("validateRole; member role is null;");
        }
    }

}
