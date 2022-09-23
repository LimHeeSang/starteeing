package com.starting.domain.member.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class InputUserDataRequestDto {

    private final String name;

    private final String birthOfDate;

    private final String phoneNumber;

    private final String nickname;

    private final String mbti;

    private final String school;

    private final String department;

    private final String uniqSchoolNumber;
}
