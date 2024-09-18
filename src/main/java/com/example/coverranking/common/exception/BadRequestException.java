package com.example.coverranking.common.exception;

import com.example.coverranking.common.Image.exception.ImageErrorCode;
import org.springframework.http.HttpStatus;

public class BadRequestException extends GlobalException {

    public BadRequestException(ErrorCode<?> errorCode) {
        super(errorCode, HttpStatus.BAD_REQUEST);
    }
    public static class ImageBadRequestException extends BadRequestException {

        public ImageBadRequestException(ImageErrorCode errorCode) {
            super(new ErrorCode<>(errorCode.getCode(), errorCode.getMessage()));
        }

    }

}