package com.starting.domain.friends.exception;

import com.starting.golbal.exception.business.BusinessException;

public class NotExistPreRequest extends BusinessException {

    public NotExistPreRequest() {
        super(FriendExEnum.NOT_EXIST_PRE_REQUEST);
    }
}