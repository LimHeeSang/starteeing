package com.starteeing.golbal.exception.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CommonExEnum implements ExceptionEnum {
    SUCCESS(HttpStatus.OK, "E0001", "정상 요청입니다."),
    FAIL(HttpStatus.BAD_REQUEST, "E0002", "잘못된 요청입니다."),

    INVALID_BINGING_VALUE(HttpStatus.BAD_REQUEST, "E0003", "입력값 중 바인딩이 실패하였습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "E0004", "인증에 실패했습니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "E0005", "해당 권한이 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}