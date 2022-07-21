package com.starting.domain.member.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MemberLoginResponseDto {

    private String grantType;

    private Long accessTokenExpireDate;

    private String accessToken;

    private String refreshToken;
}