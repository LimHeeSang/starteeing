package com.starting.global.oauth.userinfo;

import com.starting.domain.member.entity.GenderEnum;

import java.util.Map;

public class KakaoOAuth2UserInfo extends OAuth2UserInfo {

    public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getUserId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getName() {
        Map<String, Object> properties = getProperties();

        if (properties == null) {
            return null;
        }

        return (String) properties.get("nickname");
    }

    @Override
    public String getEmail() {
        Map<String, Object> attributes = getAttributes();

        if (attributes == null) {
            return null;
        }

        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");

        if (kakaoAccount == null) {
            return null;
        }

        return (String) kakaoAccount.get("email");
    }

    @Override
    public String getImageUrl() {
        Map<String, Object> properties = getProperties();

        if (properties == null) {
            return null;
        }

        return (String) properties.get("profile_image");
    }

    @Override
    public GenderEnum getGender() {
        Map<String, Object> attributes = getAttributes();

        if (attributes == null) {
            return null;
        }

        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");

        if (kakaoAccount == null) {
            return null;
        }

        String gender = (String) kakaoAccount.get("gender");
        if (gender.equals("male")) {
            return GenderEnum.MALE;
        }
        if (gender.equals("female")) {
            return GenderEnum.FEMALE;
        }
        return null;
    }

    private Map<String, Object> getProperties() {
        return (Map<String, Object>) attributes.get("properties");
    }
}