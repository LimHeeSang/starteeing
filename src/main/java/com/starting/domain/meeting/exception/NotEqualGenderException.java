package com.starting.domain.meeting.exception;

import com.starting.golbal.exception.business.BusinessException;

public class NotEqualGenderException extends BusinessException {

    public NotEqualGenderException() {
        super(TicketExEnum.NOT_EQUAL_GENDER);
    }
}
