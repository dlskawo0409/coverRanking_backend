//package com.example.coverranking.common.storage.presentation;
//
//import com.example.coverranking.common.storage.application.S3Service;
//import lombok.AllArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//@RestController
//@AllArgsConstructor
//@RequestMapping("/s3")
//public class S3Controller {
//    private final S3Service s3Service;
//
//    /* Controller */
//    @PostMapping
//    public ResponseEntity<?> submitFiles (@RequestParam("images") List<MultipartFile> multipartFileList) throws IOException {
//        List<String> imageUrlList = new ArrayList<>();
//
//        for (MultipartFile multipartFile : multipartFileList) {
//            // 파일명 지정 (겹치면 안되고, 확장자 빼먹지 않도록 조심!)
//            String fileName = UUID.randomUUID() + multipartFile.getOriginalFilename();
//            // 파일데이터와 파일명 넘겨서 S3에 저장
//            s3Service.upload(multipartFile, fileName);
//            // DB에는 전체 url말고 파일명으로 저장할 것임
//            imageUrlList.add(fileName);
//        }
//
//        return ResponseEntity.ok().build();
//    }
//
//
//}
