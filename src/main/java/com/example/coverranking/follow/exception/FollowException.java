package com.example.coverranking.follow.exception;

import com.example.coverranking.common.exception.BadRequestException;
import com.example.coverranking.common.exception.ErrorCode;


public class FollowException {

    public static class FollowBadRequestException extends BadRequestException {
        public FollowBadRequestException(FollowErrorCode errorCode) {
            super(new ErrorCode<>(errorCode.getCode(), errorCode.getMessage()));
        }
    }

}
