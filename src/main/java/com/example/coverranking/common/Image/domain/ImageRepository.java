package com.example.coverranking.common.Image.domain;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {

    Optional<Image> findByImageId(Long imageId);
}
