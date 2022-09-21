package com.starting.domain.member.exception;

import com.starting.global.exception.business.BusinessException;

public class ExistMemberException extends BusinessException {

    public ExistMemberException() {
        super(MemberExEnum.ALREADY_EXIST_MEMBER);
    }
}