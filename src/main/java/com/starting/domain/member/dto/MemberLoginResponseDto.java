package com.starting.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@SuperBuilder
@Getter
public class MemberLoginResponseDto {

    private String grantType;

    private Long accessTokenExpireDate;

    private String accessToken;

    private String refreshToken;

}