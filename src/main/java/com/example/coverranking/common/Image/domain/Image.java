package com.example.coverranking.common.Image.domain;

import com.example.coverranking.comment.BasicEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Table(name = "image")
@Entity
@Builder
@Getter
public class Image extends BasicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IMAGE_ID",updatable = false)
    private Long imageId;

    @Column(name = "IMAGE_URL")
    private String imageUrl;


    public static String makeImageName(MultipartFile multipartFile){
        return UUID.randomUUID() +
                ImageExtension.from(multipartFile.getOriginalFilename());
    }
}
