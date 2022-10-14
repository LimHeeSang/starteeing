package com.starting.domain.friends.exception;

import com.starting.global.exception.common.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum FriendExEnum implements ExceptionEnum {
    NOT_EXIST_FRIEND(HttpStatus.BAD_REQUEST, "F0001", "찾으려는 친구가 없습니다."),
    NOT_EXIST_PREVIOUS_REQUEST(HttpStatus.BAD_REQUEST, "F0002", "이전 친구 요청이 없습니다."),
    ALREADY_REQUEST_FRIEND(HttpStatus.BAD_REQUEST, "F0003", "이미 친구요청을 보낸 회원입니다."),
    ALREADY_FRIEND(HttpStatus.BAD_REQUEST, "F0004", "이미 친구인 회원입니다."),
    ALREADY_RESPONSE_FRIEND(HttpStatus.BAD_REQUEST, "F0005", "이미 친구요청을 받은 회원입니다. 친구수락목록을 확인하세요");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
