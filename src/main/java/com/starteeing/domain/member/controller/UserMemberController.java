package com.starteeing.domain.member.controller;

import com.starteeing.domain.member.dto.MemberLoginResponseDto;
import com.starteeing.domain.member.dto.UserMemberRequestDto;
import com.starteeing.domain.member.service.UserMemberService;
import com.starteeing.golbal.response.ResponseService;
import com.starteeing.golbal.response.result.CommonResult;
import com.starteeing.golbal.response.result.ListResult;
import com.starteeing.golbal.response.result.SingleResult;
import com.starteeing.golbal.security.JwtProvider;
import com.starteeing.domain.member.dto.MemberLoginRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserMemberController {

    private final UserMemberService userMemberService;
    private final ResponseService responseService;

    @PostMapping("/members")
    public ResponseEntity<CommonResult> signup(@RequestBody @Validated UserMemberRequestDto memberRequestDto) {
        userMemberService.memberJoin(memberRequestDto);
        return ResponseEntity.ok(responseService.getSuccessResult());
    }

    @PostMapping("/login")
    public ResponseEntity<SingleResult> login(@RequestBody @Validated MemberLoginRequestDto loginRequestDto) {
        MemberLoginResponseDto loginResponseDto = userMemberService.login(loginRequestDto);

        return ResponseEntity.ok(responseService.getSingleResult(loginResponseDto));
    }
}