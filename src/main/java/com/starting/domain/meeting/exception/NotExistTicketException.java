package com.starting.domain.meeting.exception;

import com.starting.golbal.exception.business.BusinessException;

public class NotExistTicketException extends BusinessException {

    public NotExistTicketException() {
        super(TicketExEnum.NOT_EXIST_TICKET);
    }
}
