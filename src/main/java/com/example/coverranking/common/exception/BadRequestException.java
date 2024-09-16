package com.example.coverranking.common.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends GlobalException {

    public BadRequestException(ErrorCode<?> errorCode) {
        super(errorCode, HttpStatus.BAD_REQUEST);
    }


}