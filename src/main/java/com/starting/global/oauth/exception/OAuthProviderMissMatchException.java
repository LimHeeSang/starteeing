package com.starting.global.oauth.exception;

import com.starting.global.exception.business.BusinessException;

public class OAuthProviderMissMatchException extends BusinessException {

    public OAuthProviderMissMatchException() {
        super(OauthExEnum.PROVIDER_MISS_MATCH);
    }
}