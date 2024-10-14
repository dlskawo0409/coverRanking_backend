package com.example.coverranking.follow.domain;

import com.example.coverranking.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
public class Follow {
    @Id
    @Column(name = "FOLLOW_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long followId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FOLLOWING")
    private Member following;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FOLLOWER")
    private Member follower;

}
