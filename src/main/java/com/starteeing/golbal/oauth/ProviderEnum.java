package com.starteeing.golbal.oauth;

import lombok.Getter;

@Getter
public enum ProviderEnum {
    GOOGLE,
    NAVER,
    KAKAO,
    LOCAL;

    public boolean isKakao() {
        return this.equals(KAKAO);
    }

    public boolean isNaver() {
        return this.equals(NAVER);
    }

    public boolean isGoogle() {
        return this.equals(GOOGLE);
    }
}