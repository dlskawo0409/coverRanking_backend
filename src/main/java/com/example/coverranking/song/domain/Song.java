package com.example.coverranking.song.domain;

import com.example.coverranking.member.domain.Genre;
import jakarta.persistence.*;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Builder
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SONG_ID")
    private Long songId;

    @Column(name = "TITLE" ,nullable = false)
    private String title;

    @Column(name ="SINGER", nullable = false)
    private String singer;

    @Column(name = "RELEASE_DATE")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime release_at;

    @Column(name = "GENRE")
    private Genre genre;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.release_at = now;
    }

}
