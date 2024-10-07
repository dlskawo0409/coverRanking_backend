package com.example.coverranking.common.exception;

import org.springframework.http.HttpStatus;
public class ConflictException extends GlobalException {

    public ConflictException(ErrorCode<?> errorCode) {
        super(errorCode, HttpStatus.CONFLICT);
    }

}