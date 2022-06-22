package com.starteeing.domain.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberReissueRequestDto {

    private String accessToken;

    private String refreshToken;
}