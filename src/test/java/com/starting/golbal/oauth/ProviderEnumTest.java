package com.starting.golbal.oauth;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProviderEnumTest {

    @Test
    void isKakao() {
        ProviderEnum kakao = ProviderEnum.KAKAO;

        assertThat(kakao.isKakao()).isTrue();
    }

    @Test
    void isNaver() {
        ProviderEnum kakao = ProviderEnum.NAVER;

        assertThat(kakao.isNaver()).isTrue();
    }

    @Test
    void isGoogle() {
        ProviderEnum kakao = ProviderEnum.GOOGLE;

        assertThat(kakao.isGoogle()).isTrue();
    }
}