package com.starting.domain.friends.exception;

import com.starting.golbal.exception.common.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum FriendExEnum implements ExceptionEnum {
    NOT_EXIST_FRIEND(HttpStatus.BAD_REQUEST, "F0001", "찾으려는 친구가 없습니다."),
    NOT_EXIST_PRE_REQUEST(HttpStatus.BAD_REQUEST, "F0002", "이전 요청이 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
