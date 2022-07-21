package com.starting.domain.member.controller;

import com.starting.domain.member.dto.MemberLoginRequestDto;
import com.starting.domain.member.dto.MemberLoginResponseDto;
import com.starting.domain.member.dto.MemberReissueRequestDto;
import com.starting.domain.member.dto.UserMemberSignupRequestDto;
import com.starting.domain.member.service.UserMemberService;
import com.starting.golbal.response.ResponseService;
import com.starting.golbal.response.result.CommonResult;
import com.starting.golbal.response.result.SingleResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/test")
    public ResponseEntity<CommonResult> test() {
        return ResponseEntity.ok(responseService.getSingleResult("test가 성공했습니다."));
    }
}