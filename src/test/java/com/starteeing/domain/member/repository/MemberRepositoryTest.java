package com.starteeing.domain.member.repository;

import com.starteeing.domain.member.entity.*;
import com.starteeing.golbal.security.JwtProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private UserMemberRepository userMemberRepository;

    private UserMember userMember;

    @BeforeEach
    void setUp() {
        userMember = UserMember.builder()
                .name("홍길동")
                .email("abc@naver.com")
                .password("1234")
                .nickName("길동이")
                .birthOfDate(LocalDate.of(1998, 9, 4))
                .refreshToken(createTestRefreshToken())
                .phoneNumber("010-8543-0619")
                .mbti("estj")
                .schoolInfo(createSchoolInfo())
                .build();

        userMemberRepository.save(userMember);
    }

    private SchoolInfo createSchoolInfo() {
        SchoolInfo schoolInfo = SchoolInfo.builder()
                .school("순천향대")
                .department("정보보호학과")
                .uniqSchoolNumber("12345678")
                .build();
        return schoolInfo;
    }

    private RefreshToken createTestRefreshToken() {
        return RefreshToken.builder().refreshToken("Test_Refresh_Token_Value").build();
    }

    @Test
    void existsByEmail() {
        boolean result = memberRepository.existsByEmail("abc@naver.com");
        assertThat(result).isTrue();
    }

    @Test
    void findByEmailWithMemberRoles() {
        Member member = memberRepository.findByEmailWithMemberRoles("abc@naver.com").get();

        assertThat(member.getMemberRoles()).containsOnly(new MemberRole(MemberRoleEnum.ROLE_USER));
    }

    @Test
    void findByEmailWithRefreshToken() {
        Member member = memberRepository.findByEmailWithRefreshToken("abc@naver.com").get();

        assertThat(member.getRefreshToken().get().getRefreshTokenValue()).isEqualTo("Test_Refresh_Token_Value");
    }
}