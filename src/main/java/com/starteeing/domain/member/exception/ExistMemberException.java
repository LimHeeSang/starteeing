package com.starteeing.domain.member.exception;

import com.starteeing.golbal.exception.business.BusinessException;

public class ExistMemberException extends BusinessException {

    public ExistMemberException() {
        super(MemberExEnum.ALREADY_EXIST_MEMBER);
    }
}