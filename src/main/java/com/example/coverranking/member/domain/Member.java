package com.example.coverranking.member.domain;

import com.example.coverranking.comment.BasicEntity;
import com.example.coverranking.common.Image.domain.Image;
import com.example.coverranking.common.util.RegexUtil;
import com.example.coverranking.follow.domain.Follow;
import com.example.coverranking.member.exception.MemberException.*;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

import static com.example.coverranking.member.exception.MemberErrorCode.*;

@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Inheritance(strategy = InheritanceType.JOINED)
public class Member extends BasicEntity implements UserDetails  {

    private static final String VALID_EMAIL_URL_REGEX = "^[a-zA-Z0-9]+@[a-zA-Z]+\\.[a-zA-Z]{2,}$";
    private static final int NICKNAME_LENGTH = 20;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID", updatable = false)
    private Long memberId;

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


    @ElementCollection(targetClass = Genre.class)
    @CollectionTable(name = "MEMBER_PREFERRED_GENRES", joinColumns = @JoinColumn(name = "MEMBER_ID"))
    @Enumerated(EnumType.STRING)
    @Column(name = "PREFERRED_GENRE")
    private List<Genre> preferredGenres;


    @Column(name = "IS_BLOCKED")
    private Blocked isBlocked;

    @Column(name="ROLE", nullable = false)
    private Role role;

    @OneToOne( cascade = CascadeType.ALL)
    @JoinColumn(name = "IMAGE_ID")
    private Image profile;

    @OneToMany(mappedBy = "following")
    private List<Follow> following;

    @OneToMany(mappedBy = "follower")
    private List<Follow> follower;


    // 생성자에서 @Builder 제거
    public Member(String email, String password, String nickname, Role role) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.getKey()));
    }

    @Override
    public String getUsername() {
        return email;
    }
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return memberId.equals(member.memberId) && email.equals(member.email) && nickname.equals(member.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId, email, nickname);
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
