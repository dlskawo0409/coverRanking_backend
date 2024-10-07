package com.example.coverranking.common.Image.domain;



import com.example.coverranking.common.exception.BadRequestException.*;

import java.util.Arrays;

import static com.example.coverranking.common.Image.exception.ImageErrorCode.ILLEGAL_IMAGE_FILE_EXTENSION;

public enum ImageExtension {

    JPEG(".jpeg"),
    JPG(".jpg"),
    JFIF(".jfif"),
    PNG(".png"),
    SVG(".svg"),
    ;


    private final String extension;

    ImageExtension(final String extension) {
        this.extension = extension;
    }

    public static String from(String imageFileName) {
        boolean isValidExtension = Arrays.stream(values())
                .anyMatch(imageExtension -> imageFileName.endsWith(imageExtension.getExtension()));

        if (isValidExtension) {
            return imageFileName;
        } else {
            throw new ImageBadRequestException(ILLEGAL_IMAGE_FILE_EXTENSION);
        }
    }


    public String getExtension() {
        return extension;
    }

}