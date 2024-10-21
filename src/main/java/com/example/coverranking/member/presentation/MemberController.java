package com.example.coverranking.member.presentation;



import com.example.coverranking.common.storage.application.S3Service;
import com.example.coverranking.member.application.MemberService;
import com.example.coverranking.member.dto.request.MemberEmail;
import com.example.coverranking.member.dto.request.MemberNickName;

import com.example.coverranking.member.dto.response.MemberNickNameExistResponse;

import com.example.coverranking.member.dto.request.AddMemberRequest;
import com.example.coverranking.member.dto.response.MemberResponse;
import com.example.coverranking.member.dto.response.MemberUpdateResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> checkEmail(@RequestBody MemberEmail memberEmail){
        var memberExistResponse = memberService.duplicateEmailService(memberEmail.getEmail());
        return ResponseEntity.ok(memberExistResponse);
    }

    @PostMapping("/check-nickname")
    public ResponseEntity<?> checkNickname(@RequestBody MemberNickName memberNickName){
        boolean isExist = memberService.duplicateNicknameService(memberNickName.getNickName());

        // 200 OK 상태와 함께 응답 반환
        return ResponseEntity.ok(new MemberNickNameExistResponse(isExist));
    }

    @PutMapping("/profile")
    public ResponseEntity<Map<String, String>> modifyProfile(
            @RequestPart("image") MultipartFile multipartFile,
            @RequestHeader("access") String token
    ){
        String url = memberService.updateProfile(token, multipartFile);
        Map<String, String> response = new HashMap<>();
        response.put("profile", url);
        return ResponseEntity.ok(response);
    }

    @PutMapping(
            value = "",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public ResponseEntity<MemberUpdateResponse> modifyMember(
            @RequestPart("member") AddMemberRequest addMemberRequest,
            @RequestPart(value = "image", required = false) MultipartFile multipartFile,
            @RequestHeader("access") String token
    ) {
        MemberUpdateResponse result = memberService.updateMember(addMemberRequest, multipartFile, token);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("")
    public ResponseEntity<Map<String, String>> deleteMember(@RequestHeader("access") String token){
        String result = memberService.deleteMember(token);
        Map<String, String> response = new HashMap<>();
        response.put("userState", result);
        return ResponseEntity.ok(response);
    }

}
