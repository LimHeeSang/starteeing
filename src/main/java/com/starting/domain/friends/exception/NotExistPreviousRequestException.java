package com.starting.domain.friends.exception;

import com.starting.global.exception.business.BusinessException;

public class NotExistPreviousRequestException extends BusinessException {

    public NotExistPreviousRequestException() {
        super(FriendExEnum.NOT_EXIST_PREVIOUS_REQUEST);
    }
}