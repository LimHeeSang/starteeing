package com.starting.domain.meeting.exception;

import com.starting.golbal.exception.common.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum TicketExEnum implements ExceptionEnum {
    NOT_EXIST_TICKET(HttpStatus.BAD_REQUEST, "T0001", "존재하지 않는 티켓입니다."),
    ALREADY_EXIST_TICKET(HttpStatus.BAD_REQUEST, "T0002", "이미 존재하는 티켓입니다."),
    NOT_EQUAL_GENDER(HttpStatus.BAD_REQUEST, "T0003", "티켓은 같은 성별로 이루어진 팀으로 생성할 수 있습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}