package com.example.coverranking.member.dto.response;

public record MemberExistResponse(
        boolean isExist
) {

     public static MemberExistResponse from(boolean isExist){
          return new MemberExistResponse(isExist);
     }

}
