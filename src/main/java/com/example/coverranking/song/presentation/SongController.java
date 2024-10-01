package com.example.coverranking.song.presentation;

import com.example.coverranking.song.application.SongService;
import com.example.coverranking.song.dto.request.AddSong;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/songs")
@RequiredArgsConstructor
public class SongController {

}
