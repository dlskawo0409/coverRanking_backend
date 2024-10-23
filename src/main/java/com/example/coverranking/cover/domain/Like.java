package com.example.coverranking.cover.domain;

import com.example.coverranking.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Inheritance(strategy = InheritanceType.JOINED)
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LIKE_ID", updatable = false)
    private Long likeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COVER")
    private Cover cover;


}
