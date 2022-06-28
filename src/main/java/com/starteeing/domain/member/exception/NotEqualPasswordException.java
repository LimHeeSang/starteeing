package com.starteeing.domain.member.exception;

import com.starteeing.golbal.exception.business.BusinessException;

public class NotEqualPasswordException extends BusinessException {

    public NotEqualPasswordException() {
        super(MemberExEnum.NOT_EQUAL_PASSWORD);
    }
}