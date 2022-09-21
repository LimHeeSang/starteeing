package com.starting.domain.member.exception;

import com.starting.global.exception.business.BusinessException;

public class NotExistMemberException extends BusinessException {

    public NotExistMemberException() {
        super(MemberExEnum.NOT_EXIST_MEMBER);
    }
}