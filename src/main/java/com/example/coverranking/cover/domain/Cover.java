package com.example.coverranking.cover.domain;

import com.example.coverranking.comment.BasicEntity;
import com.example.coverranking.common.Image.domain.Image;
import com.example.coverranking.member.domain.Genre;
import com.example.coverranking.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "cover")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Inheritance(strategy = InheritanceType.JOINED)
public class Cover  extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COVER_ID", updatable = false)
    private Long coverId;

    @Column(name = "RANKING")
    private Long ranking;

    @OneToMany(mappedBy = "cover")
    private List<Like> likes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Member")
    private Member singer;

    @ManyToOne(fetch = FetchType.LAZY)
    private Genre genre;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "COVER_IMAGE")
    private Image coverImage;



}
