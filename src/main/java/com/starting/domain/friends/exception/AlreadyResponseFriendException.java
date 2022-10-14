package com.starting.domain.friends.exception;

import com.starting.global.exception.business.BusinessException;

public class AlreadyResponseFriendException extends BusinessException {

    public AlreadyResponseFriendException() {
        super(FriendExEnum.ALREADY_RESPONSE_FRIEND);
    }
}
