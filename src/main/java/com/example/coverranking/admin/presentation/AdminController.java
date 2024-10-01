package com.example.coverranking.admin.presentation;

import com.example.coverranking.song.application.SongService;
import com.example.coverranking.song.dto.request.AddSong;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")  // 클래스 레벨에서 경로 설정
@RequiredArgsConstructor
public class AdminController {
    private final SongService songService;

    @PostMapping("/songs")
    public ResponseEntity<?> registSong(@RequestBody AddSong addSong){
        songService.insertSong(addSong);
        return new ResponseEntity<>("노래가 성공적으로 추가되었습니다.", HttpStatus.CREATED);
    }
}
