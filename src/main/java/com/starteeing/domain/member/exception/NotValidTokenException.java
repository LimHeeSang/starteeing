package com.starteeing.domain.member.exception;

import com.starteeing.golbal.exception.business.BusinessException;

public class NotValidTokenException extends BusinessException {

    public NotValidTokenException() {
        super(MemberExEnum.NOT_VALID_TOKEN);
    }
}