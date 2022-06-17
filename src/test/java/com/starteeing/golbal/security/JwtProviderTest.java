package com.starteeing.golbal.security;

import com.starteeing.domain.member.dto.UserMemberRequestDto;
import com.starteeing.domain.member.entity.UserMember;
import com.starteeing.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class JwtProviderTest {

    private static final String EMAIL = "abc@naver.com";
    public static final String PASSWORD = "1234";

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    UserMemberRequestDto userMemberRequestDto;

    @Autowired
    AuthenticationManagerBuilder authenticationManagerBuilder;

    @BeforeEach
    void setUp() {
        UserMember userMember = createUserMemberRequestDto().toEntity(bCryptPasswordEncoder);
        memberRepository.save(userMember);

        userMemberRequestDto = createUserMemberRequestDto();
    }

    @Test
    void createToken() {
        String testToken = createTestToken();
    }

    @Test
    void getAuthentication() {
        String jwtToken = createTestToken();

        Authentication authentication = jwtProvider.getAuthentication(jwtToken);

        assertThat(authentication.isAuthenticated()).isTrue();
        assertThat(authentication.getName()).isEqualTo(EMAIL);
    }

    @Test
    void validateToken() {
        String jwtToken = createTestToken();

        boolean result = jwtProvider.validateToken(jwtToken);

        assertThat(result).isTrue();
    }

    private String createTestToken() {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userMemberRequestDto.getEmail(), userMemberRequestDto.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        return jwtProvider.createToken(authentication).getAccessToken();
    }

    private UserMemberRequestDto createUserMemberRequestDto() {
        return UserMemberRequestDto.builder()
                .name("홍길동")
                .email(EMAIL)
                .password(PASSWORD)
                .nickname("길동이")
                .birthOfDate("1998-09-04")
                .phoneNumber("010-8543-0619")
                .mbti("estj")
                .school("순천향대")
                .department("정보보호학과")
                .uniqSchoolNumber("12345678")
                .build();
    }
}