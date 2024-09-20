package com.example.coverranking.follow.domain;

import com.example.coverranking.member.domain.Member;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
public class Follow {
    @Id
    @Column(name = "FOLLOW_ID")
    private Long followId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FOLLOWING")
    private Member following;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FOLLOWER")
    private Member follower;



}