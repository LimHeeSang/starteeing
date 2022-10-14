package com.starting.domain.friends.exception;

import com.starting.global.exception.business.BusinessException;

public class AlreadyRequestFriendException extends BusinessException {

    public AlreadyRequestFriendException() {
        super(FriendExEnum.ALREADY_REQUEST_FRIEND);
    }
}

