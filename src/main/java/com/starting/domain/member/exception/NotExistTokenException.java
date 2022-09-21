package com.starting.domain.member.exception;

import com.starting.global.exception.business.BusinessException;

public class NotExistTokenException extends BusinessException {

    public NotExistTokenException() {
        super(MemberExEnum.NOT_EXIST_TOKEN);
    }
}