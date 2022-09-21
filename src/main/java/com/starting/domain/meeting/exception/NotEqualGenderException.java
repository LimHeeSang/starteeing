package com.starting.domain.meeting.exception;

import com.starting.global.exception.business.BusinessException;

public class NotEqualGenderException extends BusinessException {

    public NotEqualGenderException() {
        super(TicketExEnum.NOT_EQUAL_GENDER);
    }
}
