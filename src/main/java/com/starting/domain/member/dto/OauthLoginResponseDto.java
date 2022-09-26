package com.starting.domain.member.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class OauthLoginResponseDto extends MemberLoginResponseDto {

    private final Long memberId;

    public OauthLoginResponseDto(String grantType, Long accessTokenExpireDate, String accessToken, String refreshToken, Long memberId) {
        super(grantType, accessTokenExpireDate, accessToken, refreshToken);
        this.memberId = memberId;
    }
}
