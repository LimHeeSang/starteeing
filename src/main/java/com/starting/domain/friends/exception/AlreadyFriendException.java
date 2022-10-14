package com.starting.domain.friends.exception;

import com.starting.global.exception.business.BusinessException;

public class AlreadyFriendException extends BusinessException {

    public AlreadyFriendException() {
        super(FriendExEnum.ALREADY_FRIEND);
    }
}
