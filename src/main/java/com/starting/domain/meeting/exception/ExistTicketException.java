package com.starting.domain.meeting.exception;

import com.starting.golbal.exception.business.BusinessException;

public class ExistTicketException extends BusinessException {

    public ExistTicketException() {
        super(TicketExEnum.ALREADY_EXIST_TICKET);
    }
}
