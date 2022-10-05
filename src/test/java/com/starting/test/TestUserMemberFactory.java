package com.starting.test;

import com.starting.domain.member.entity.RefreshToken;
import com.starting.domain.member.entity.SchoolInfo;
import com.starting.domain.member.entity.UserMember;
import com.starting.global.oauth.ProviderEnum;

import java.time.LocalDate;

public final class TestUserMemberFactory {

    private static int count = 0;

    private TestUserMemberFactory() {
    }

    public static UserMember create() {
        count++;
        return createUserMember();
    }

    private static UserMember createUserMember() {
        return UserMember.builder()
                .name("테스트 유저" + count)
                .email("테스트 이메일" + count)
                .userId("테스트 유저 id" + count)
                .password("테스트 비밀번호" + count)
                .imageProfileUrl("테스트 이미지 프로필" + count)
                .providerEnum(ProviderEnum.KAKAO)
                .nickName("테스트 닉네임" + count)
                .birthOfDate(LocalDate.of(1998, 9, 4 + count))
                .phoneNumber("테스트 전화번호" + count)
                .refreshToken(createTestRefreshToken())
                .mbti("estj")
                .temperature(36.5D)
                .schoolInfo(createSchoolInfo())
                .build();
    }

    private static RefreshToken createTestRefreshToken() {
        return RefreshToken.builder().refreshToken("Test_Refresh_Token_Value" + count).build();
    }

    private static SchoolInfo createSchoolInfo() {
        return SchoolInfo.builder()
                .school("테스트 학교" + count)
                .department("테스트 학과" + count)
                .uniqSchoolNumber("테스트 학번" + count)
                .build();
    }
}
