package com.example.coverranking.song.application;

import com.example.coverranking.song.domain.SongRepository;
import com.example.coverranking.song.domain.Song;
import com.example.coverranking.song.dto.request.AddSong;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SongService {

    private final SongRepository songRepository;

    public void insertSong(AddSong addSong){
        songRepository.save(Song.builder()
                .title(addSong.getTitle())
                .singer(addSong.getSinger())
                .genre(addSong.getGenre())
                .build());
    }

}
