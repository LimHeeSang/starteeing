package com.starting.domain.member.exception;

import com.starting.global.exception.business.BusinessException;

public class AlreadyInputUserdata extends BusinessException {

    public AlreadyInputUserdata() {
        super(MemberExEnum.ALREADY_INPUT_USERDATA);
    }
}
