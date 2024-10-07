package com.example.coverranking.song.dto.request;

import com.example.coverranking.member.domain.Genre;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AddSong {

    @NotBlank(message = "제목이 공백이 될 수 없습니다.")
    private final String title;

    @NotBlank(message = "가수는 공백이 될 수 없습니다.")
    private final String singer;

    @NotBlank(message = "장르는 공백이 될 수 없습니다.")
    private final Genre genre;

}
