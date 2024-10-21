package com.example.coverranking.member.presentation;



import com.example.coverranking.common.storage.application.S3Service;
import com.example.coverranking.member.application.MemberService;
import com.example.coverranking.member.dto.request.CustomMemberDetails;
import com.example.coverranking.member.dto.request.MemberEmailExistRequest;
import com.example.coverranking.member.dto.request.MemberNickNameExistRequest;

import com.example.coverranking.member.dto.response.*;

import com.example.coverranking.member.dto.request.AddMemberRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final S3Service s3Service;

    @GetMapping("")
    public ResponseEntity<List<MemberResponse>> getMembers(@RequestParam String nickname) {
        List<MemberResponse> members = memberService.selectByNickname(nickname);
        return ResponseEntity.ok(members);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> joinMember(@Valid @RequestPart("member")AddMemberRequest addMemberRequest,
                                        @RequestPart("image") MultipartFile multipartFile) {
        memberService.createMemberService(addMemberRequest, multipartFile);
        return new ResponseEntity<>("회원이 성공적으로 추가되었습니다.", HttpStatus.CREATED);
    }

    @PostMapping("/check-email")
    public ResponseEntity<?> checkEmail(@RequestBody MemberEmailExistRequest memberEmailExistRequest){
        boolean isExist = memberService.duplicateEmailService(memberEmailExistRequest.email());
        var memberExistResponse = MemberEmailExistResponse.from(isExist);
        return ResponseEntity.ok(memberExistResponse);
    }

    @PostMapping("/check-nickname")
    public ResponseEntity<?> checkNickname(@RequestBody MemberNickNameExistRequest memberNickNameExistRequest){
        boolean isExist = memberService.duplicateNicknameService(memberNickNameExistRequest.nickName());
        return ResponseEntity.ok(new MemberNickNameExistResponse(isExist));
    }

    @PutMapping("/profile")
    public ResponseEntity<?> modifyProfile(
            @RequestPart("image") MultipartFile multipartFile,
            @AuthenticationPrincipal CustomMemberDetails loginMember
    ){
        var memberProfileUpdateResponse = memberService.updateProfile(loginMember, multipartFile);
        return ResponseEntity.ok(memberProfileUpdateResponse);
    }

    @PutMapping(
            value = "",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public ResponseEntity<?> modifyMember(
            @RequestPart("member") AddMemberRequest addMemberRequest,
            @RequestPart(value = "image", required = false) MultipartFile multipartFile,
            @AuthenticationPrincipal CustomMemberDetails loginMember
    ) {
        var memberUpdateResponse = memberService.updateMember(addMemberRequest, multipartFile, loginMember);
        return ResponseEntity.ok(memberUpdateResponse);
    }

    @DeleteMapping("")
    public ResponseEntity<?> removeMember(@AuthenticationPrincipal CustomMemberDetails loginMember){
        String result = memberService.deleteMember(loginMember);
        var memberDeleteResponse = MemberDeleteResponse.from(result);
        return ResponseEntity.ok(memberDeleteResponse);
    }

}
