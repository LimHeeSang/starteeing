package com.starteeing.golbal.oauth.exception;

import com.starteeing.golbal.exception.business.BusinessException;

public class OAuthProviderMissMatchException extends BusinessException {

    public OAuthProviderMissMatchException() {
        super(OauthExEnum.PROVIDER_MISS_MATCH);
    }
}