package com.starting.global.oauth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.util.List;

@ConstructorBinding
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "app")
public final class AppProperties {

    private final Jwt jwt;
    private final Oauth2 oauth2;

    public String  getJwtTokenSecret() {
        return jwt.getTokenSecret();
    }

    public Long getJwtAccessTokenExpire() {
        return jwt.getAccessTokenExpire();
    }

    public Long getJwtRefreshTokenExpire() {
        return jwt.getRefreshTokenExpire();
    }

    public List<String> getOauth2AuthorizedRedirectUris() {
        return oauth2.getAuthorizedRedirectUris();
    }

    @Getter
    @RequiredArgsConstructor
    private static class Jwt {
        private final String tokenSecret;
        private final Long accessTokenExpire;
        private final Long refreshTokenExpire;
    }

    @Getter
    @RequiredArgsConstructor
    private static class Oauth2 {
        private final List<String> authorizedRedirectUris;
    }

}
