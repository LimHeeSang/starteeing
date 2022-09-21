package com.starting.domain.member.controller;

import com.starting.domain.member.dto.*;
import com.starting.domain.member.service.UserMemberService;
import com.starting.golbal.response.ResponseService;
import com.starting.golbal.response.result.CommonResult;
import com.starting.golbal.response.result.SingleResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserMemberController {

    private final UserMemberService userMemberService;
    private final ResponseService responseService;

    @PostMapping("/signup")
    public ResponseEntity<CommonResult> signup(@RequestBody @Validated UserMemberSignupRequestDto memberRequestDto) {
        userMemberService.memberJoin(memberRequestDto);
        return ResponseEntity.ok(responseService.getSuccessResult());
    }

    @PostMapping("/login")
    public ResponseEntity<SingleResult> login(@RequestBody @Validated MemberLoginRequestDto loginRequestDto) {
        MemberLoginResponseDto loginResponseDto = userMemberService.login(loginRequestDto);
        return ResponseEntity.ok(responseService.getSingleResult(loginResponseDto));
    }

    @PostMapping("/reissue")
    public ResponseEntity<SingleResult> reissue(@RequestBody @Validated MemberReissueRequestDto reissueRequestDto) {
        MemberLoginResponseDto memberLoginResponseDto = userMemberService.reissue(reissueRequestDto);
        return ResponseEntity.ok(responseService.getSingleResult(memberLoginResponseDto));
    }

    @GetMapping("/inputted/{memberId}")
    public ResponseEntity<SingleResult> isInputUserData(@PathVariable Long memberId) {
        IsInputUserDataResponseDto isInputUserDataResponseDto = userMemberService.isInputUserData(memberId);
        return ResponseEntity.ok(responseService.getSingleResult(isInputUserDataResponseDto));
    }

    @PostMapping("/inputs/{memberId}")
    public ResponseEntity<CommonResult> inputUserData(@PathVariable Long memberId, @RequestBody @Validated InputUserDataRequestDto inputUserDataRequestDto) {
        userMemberService.inputUserData(memberId, inputUserDataRequestDto);
        return ResponseEntity.ok(responseService.getSuccessResult());
    }

    @GetMapping("/duplicate/{memberId}/{nickname}")
    public ResponseEntity<SingleResult> idDuplicateNickname(@PathVariable Long memberId, @PathVariable String nickname) {
        boolean result = userMemberService.isDuplicateNickname(memberId, nickname);
        return ResponseEntity.ok(responseService.getSingleResult(result));
    }
}