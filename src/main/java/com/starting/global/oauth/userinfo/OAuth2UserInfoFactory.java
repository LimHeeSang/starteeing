package com.starting.global.oauth.userinfo;

import com.starting.global.oauth.ProviderEnum;
import com.starting.global.oauth.exception.OAuthProviderMissMatchException;

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