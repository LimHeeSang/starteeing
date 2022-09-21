package com.starting.domain.member.exception;

import com.starting.global.exception.business.BusinessException;

public class NotEqualPasswordException extends BusinessException {

    public NotEqualPasswordException() {
        super(MemberExEnum.NOT_EQUAL_PASSWORD);
    }
}