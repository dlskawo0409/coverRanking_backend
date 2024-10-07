package com.example.coverranking.member.exception;

import com.example.coverranking.common.exception.BadRequestException;
import com.example.coverranking.common.exception.ConflictException;
import com.example.coverranking.common.exception.ErrorCode;


public class MemberException {

    public static class MemberConflictException extends ConflictException {
        public MemberConflictException(MemberErrorCode errorCode, String value) {
            super(new ErrorCode<>(errorCode.getCode(), errorCode.getMessage(), value));
        }
    }

    public static class MemberBadRequestException extends BadRequestException {
        public MemberBadRequestException(MemberErrorCode errorCode) {
            super(new ErrorCode<>(errorCode.getCode(), errorCode.getMessage()));
        }
    }

}
