package com.starting.domain.team.exception;

import com.starting.golbal.exception.business.BusinessException;

public class NotExistTeamException extends BusinessException {

    public NotExistTeamException() {
        super(TeamExEnum.NOT_EXIST_TEAM);
    }
}
