package com.starteeing.domain.member.exception;

import com.starteeing.golbal.exception.business.BusinessException;

public class NotExistTokenException extends BusinessException {

    public NotExistTokenException() {
        super(MemberExEnum.NOT_EXIST_TOKEN);
    }
}