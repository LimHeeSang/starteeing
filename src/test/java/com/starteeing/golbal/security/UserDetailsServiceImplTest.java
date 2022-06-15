package com.starteeing.golbal.security;

import com.starteeing.domain.member.dto.UserMemberRequestDto;
import com.starteeing.domain.member.entity.UserMember;
import com.starteeing.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    private static final String EMAIL = "abc@naver.com";
    public static final String PASSWORD = "1234";

    @Mock
    MemberRepository memberRepository;

    @InjectMocks
    UserDetailsServiceImpl userDetailsService;

    UserMember member;

    @BeforeEach
    void setUp() {
        member = createUserMemberRequestDto().toEntity(new BCryptPasswordEncoder());
        Long fakeMemberId = 1L;
        ReflectionTestUtils.setField(member, "id", fakeMemberId);
    }

    @Test
    void loadUserByUsername() {
        given(memberRepository.findByEmailWithMemberRoles(any())).willReturn(Optional.ofNullable(member));
        UserDetails userDetails = userDetailsService.loadUserByUsername(EMAIL);

        assertThat(userDetails.getUsername()).isEqualTo(EMAIL);
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