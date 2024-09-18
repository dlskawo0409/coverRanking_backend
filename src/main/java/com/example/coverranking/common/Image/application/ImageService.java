package com.example.coverranking.common.Image.application;

import com.example.coverranking.common.Image.domain.Image;
import com.example.coverranking.common.Image.domain.ImageRepository;
import com.example.coverranking.common.storage.application.S3Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageService {

    private final S3Service s3Service;
    private final ImageRepository imageRepository;
    @Transactional
    public void createImageService(MultipartFile multipartFile)  {

        String imageName = Image.makeImageName(multipartFile);
        imageRepository.save(Image.builder()
                .imageUrl(imageName).build());
        try{
            s3Service.upload(multipartFile, imageName);
        } catch (IOException e) {

            throw new RuntimeException(e);
        }

    }

}
