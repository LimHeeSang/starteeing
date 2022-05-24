package com.starteeing.golbal.security;

import com.starteeing.domain.member.dto.UserMemberRequestDto;
import com.starteeing.domain.member.entity.MemberRole;
import com.starteeing.domain.member.entity.UserMember;
import com.starteeing.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JwtProviderTest {

    private static final String EMAIL = "abc@naver.com";
    public static final String PASSWORD = "1234";

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    MemberRepository memberRepository;

    UserMember member;

    @BeforeEach
    void setUp() {
        member = createUserMemberRequestDto().toEntity();
        Long fakeMemberId = 1L;
        ReflectionTestUtils.setField(member, "id", fakeMemberId);

        memberRepository.save(member);
    }

    @Test
    void createToken() {
        String jwtToken = jwtProvider.createToken(EMAIL, Arrays.asList("ROLE_USER"));
    }

    @Test
    void getAuthentication() {
        String jwtToken = jwtProvider.createToken(EMAIL, Arrays.asList("ROLE_USER"));
        Authentication authentication = jwtProvider.getAuthentication(jwtToken);

        assertThat(authentication.isAuthenticated()).isTrue();
        assertThat(authentication.getName()).isEqualTo(EMAIL);
    }

    @Test
    void validateToken() {
        String jwtToken = jwtProvider.createToken(EMAIL, Arrays.asList("ROLE_USER"));

        boolean result = jwtProvider.validateToken(jwtToken);

        assertThat(result).isTrue();
    }

    private UserMemberRequestDto createUserMemberRequestDto() {
        return UserMemberRequestDto.builder()
                .name("홍길동")
                .email(EMAIL)
                .password(PASSWORD)
                .memberRole(MemberRole.ROLE_USER)
                .nickname("길동이")
                .birthOfDate(LocalDate.of(1998, 9, 4))
                .phoneNumber("010-8543-0619")
                .mbti("estj")
                .school("순천향대")
                .department("정보보호학과")
                .uniqSchoolNumber("12345678")
                .build();
    }
}