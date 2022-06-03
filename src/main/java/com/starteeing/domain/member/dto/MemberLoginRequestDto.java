package com.starteeing.domain.member.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MemberLoginRequestDto {

    private String email;

    private String password;
}
