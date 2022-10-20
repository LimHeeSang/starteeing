package com.starting.domain.friends.controller;

import com.starting.domain.friends.dto.FriendListResponseDto;
import com.starting.domain.friends.service.FriendService;
import com.starting.global.response.ResponseService;
import com.starting.global.response.result.CommonResult;
import com.starting.global.response.result.SingleResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class FriendController {

    private final FriendService friendService;
    private final ResponseService responseService;

    /**
     * 친구 관계 리스트 조회
     */
    @GetMapping("/friend/all/{memberId}")
    public ResponseEntity<SingleResult> getFriendsList(@PathVariable Long memberId) {
        FriendListResponseDto friendsList = friendService.getFriendsList(memberId);
        return ResponseEntity.ok(responseService.getSingleResult(friendsList.getResult()));
    }

    /**
     * 수락된 친구 관계 리스트 조회
     */
    @GetMapping("/friend/all/accept/{memberId}")
    public ResponseEntity<SingleResult> getAcceptFriendsList(@PathVariable Long memberId) {
        FriendListResponseDto friendList = friendService.getAcceptFriendsList(memberId);
        return ResponseEntity.ok(responseService.getSingleResult(friendList.getResult()));
    }

    /**
     * 친구 요청
     */
    @PostMapping("/friend/request/{memberId}")
    public ResponseEntity<CommonResult> requestFriend(@PathVariable Long memberId, @RequestParam String nickname) {
        friendService.requestFriend(memberId, nickname);
        return ResponseEntity.ok(responseService.getSuccessResult());
    }

    /**
     * 친구 수락
     */
    @PostMapping("/friend/accept/{memberId}")
    public ResponseEntity<CommonResult> acceptFriend(@PathVariable Long memberId, @RequestParam String nickname) {
        friendService.acceptFriend(memberId, nickname);
        return ResponseEntity.ok(responseService.getSuccessResult());
    }

    /**
     * 친구 거절
     */
    @PostMapping("/friend/reject/{memberId}")
    public ResponseEntity<CommonResult> rejectFriend(@PathVariable Long memberId, @RequestParam String nickname) {
        friendService.rejectFriend(memberId, nickname);
        return ResponseEntity.ok(responseService.getSuccessResult());
    }

    /**
     * 친구 삭제
     */
    @PostMapping("/friend/delete/{memberId}")
    public ResponseEntity<CommonResult> deleteFriend(@PathVariable Long memberId, @RequestParam String nickname) {
        friendService.deleteFriend(memberId, nickname);
        return ResponseEntity.ok(responseService.getSuccessResult());
    }
}
