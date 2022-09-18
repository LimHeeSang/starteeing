package com.starting.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class IsInputUserDataResponseDto {

    private final Long memberId;

    private final boolean isInputData;
}