package com.starting.domain.team.exception;

import com.starting.global.exception.common.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum TeamExEnum implements ExceptionEnum {
    NOT_EXIST_TEAM(HttpStatus.BAD_REQUEST, "T0001", "존재하지 않는 팀 입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
