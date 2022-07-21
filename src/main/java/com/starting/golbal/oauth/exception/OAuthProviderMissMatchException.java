package com.starting.golbal.oauth.exception;

import com.starting.golbal.exception.business.BusinessException;

public class OAuthProviderMissMatchException extends BusinessException {

    public OAuthProviderMissMatchException() {
        super(OauthExEnum.PROVIDER_MISS_MATCH);
    }
}