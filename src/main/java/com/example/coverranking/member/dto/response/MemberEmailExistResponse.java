package com.example.coverranking.member.dto.response;

public record MemberEmailExistResponse(
        boolean isExist
) {

     public static MemberEmailExistResponse from(boolean isExist){
          return new MemberEmailExistResponse(isExist);
     }

}
