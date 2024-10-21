package com.example.coverranking.follow.presentation;

import com.example.coverranking.follow.application.FollowService;
import com.example.coverranking.follow.dto.response.FollowingDeleteResponse;
import com.example.coverranking.follow.dto.response.MemberFollowingsResponse;
import com.example.coverranking.member.dto.request.CustomMemberDetails;
import com.example.coverranking.member.exception.MemberException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/followings")
@Slf4j
public class FollowController {

    private final FollowService followService;

    @PostMapping("")
    public ResponseEntity<?> followingController(@AuthenticationPrincipal CustomMemberDetails loginMember,
                                                 @RequestBody Map<String, Long> memberIdMap) {
        followService.followingByMemberId(loginMember, memberIdMap.get("memberId"));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> getFollowings(@RequestParam String nickname) {
        List<MemberFollowingsResponse> result = followService.getFollowingsByNickName(nickname);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @DeleteMapping("")
    public ResponseEntity<?> removeFollowings(@AuthenticationPrincipal CustomMemberDetails loginMember,
                                              @RequestParam Long memberId){
        String result = followService.deleteFollowing(loginMember, memberId);
        var followingDeleteResponse = FollowingDeleteResponse.from(result);
        return new ResponseEntity<>(followingDeleteResponse, HttpStatus.OK);
    }


}
