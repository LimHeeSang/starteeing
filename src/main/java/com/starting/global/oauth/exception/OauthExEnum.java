package com.starting.global.oauth.exception;

import com.starting.global.exception.common.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum OauthExEnum implements ExceptionEnum {
    PROVIDER_MISS_MATCH(HttpStatus.BAD_REQUEST, "A0001", "잘못된 Provider 요청입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

}
