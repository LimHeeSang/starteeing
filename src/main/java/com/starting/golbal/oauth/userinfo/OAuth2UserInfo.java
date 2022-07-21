package com.starting.golbal.oauth.userinfo;

import com.starting.domain.member.entity.SchoolInfo;
import com.starting.domain.member.entity.UserMember;
import com.starting.golbal.oauth.ProviderEnum;

import java.time.LocalDate;
import java.util.Map;

public abstract class OAuth2UserInfo {
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

    public UserMember toEntity(ProviderEnum providerEnum) {
        return UserMember.builder()
                .name("홍길동")
                .userId(getUserId())
                .email("testEmail@test.com")
                .imageProfileUrl(getImageUrl())
                .password("1234")
                .providerEnum(providerEnum)
                .nickName(getName())
                .birthOfDate(LocalDate.of(1998, 9, 4))
                .phoneNumber("010-8543-0619")
                .mbti("estj")
                .temperature(37.5D)
                .schoolInfo(createSchoolInfo())
                .build();
    }

    private SchoolInfo createSchoolInfo() {
        return SchoolInfo.builder()
                .school("순천향대")
                .department("정보보호학과")
                .uniqSchoolNumber("20174544")
                .build();
    }
}