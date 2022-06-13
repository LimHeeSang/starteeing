package com.starteeing.domain.member.exception;

import com.starteeing.golbal.exception.business.BusinessException;

public class NotExistMemberException extends BusinessException {

    public NotExistMemberException() {
        super(MemberExEnum.NOT_EXIST_MEMBER);
    }
}