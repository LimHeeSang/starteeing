package com.starteeing.golbal.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CommonExEnum {
    SUCCESS(HttpStatus.OK, "E0001", "정상 요청입니다."),
    FAIL(HttpStatus.BAD_REQUEST, "E0002", "잘못된 요청입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}