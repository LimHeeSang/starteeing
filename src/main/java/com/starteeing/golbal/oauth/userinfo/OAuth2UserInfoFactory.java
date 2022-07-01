package com.starteeing.golbal.oauth.userinfo;

import com.starteeing.golbal.oauth.ProviderEnum;
import com.starteeing.golbal.oauth.exception.OAuthProviderMissMatchException;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(ProviderEnum providerEnum, Map<String, Object> attributes) {
        if (providerEnum.isKakao()) {
            return new KakaoOAuth2UserInfo(attributes);
        }
        if (providerEnum.isNaver()) {
            return new NaverOAuth2UserInfo(attributes);
        }
        if (providerEnum.isGoogle()) {
            return new GoogleOAuth2UserInfo(attributes);
        }
        throw new OAuthProviderMissMatchException();
    }
}