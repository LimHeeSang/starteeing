package com.starting.global.oauth.userinfo;

import com.starting.domain.member.entity.GenderEnum;
import com.starting.domain.member.entity.UserMember;
import com.starting.global.oauth.ProviderEnum;

import java.util.Map;

public abstract class OAuth2UserInfo {

    public static final double DEFAULT_TEMPERATURE = 37.5D;
    public static final boolean DEFAULT_INPUT_USERDATA_FLAG = false;

    protected Map<String, Object> attributes;

    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public abstract String getUserId();

    public abstract String getName();

    public abstract String getEmail();

    public abstract String getImageUrl();

    public abstract GenderEnum getGender();

    public UserMember toEntity(ProviderEnum providerEnum) {
        return UserMember.builder()
                .userId(getUserId())
                .email(getEmail())
                .imageProfileUrl(getImageUrl())
                .genderEnum(getGender())
                .providerEnum(providerEnum)
                .temperature(DEFAULT_TEMPERATURE)
                .isInputUserData(DEFAULT_INPUT_USERDATA_FLAG)
                .build();
    }
}