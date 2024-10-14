package com.example.coverranking.follow.presentation;

import com.example.coverranking.follow.application.FollowService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/followings")
public class FollowController {

    private final FollowService followService;

    @PostMapping("")
    public ResponseEntity<?> followingController(@RequestHeader("access") String token, @RequestBody Map<String, Long> memberIdMap) {
        try{
            followService.followByMemberId(memberIdMap.get("memberId"), token);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
