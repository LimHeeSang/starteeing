package com.starting.domain.member.service;

import com.starting.domain.member.dto.MemberLoginRequestDto;
import com.starting.domain.member.dto.MemberLoginResponseDto;
import com.starting.domain.member.dto.MemberReissueRequestDto;
import com.starting.domain.member.dto.UserMemberSignupRequestDto;
import com.starting.domain.member.entity.MemberRoleEnum;
import com.starting.domain.member.entity.UserMember;
import com.starting.domain.member.exception.ExistMemberException;
import com.starting.domain.member.repository.MemberRepository;
import com.starting.domain.member.repository.UserMemberRepository;
import com.starting.global.security.JwtAuthenticationProvider;
import com.starting.global.security.JwtProvider;
import com.starting.global.security.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserMemberServiceTest {

    @Mock
    UserMemberRepository userMemberRepository;
    @Mock
    MemberRepository memberRepository;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock
    JwtProvider jwtProvider;
    @Mock
    UserDetailsServiceImpl userDetailsService;
    @Mock
    JwtAuthenticationProvider jwtAuthenticationProvider;

    @InjectMocks
    UserMemberService userMemberService;

    UserMember savedMember;

    @BeforeEach
    void setUp() {
        savedMember = createUserMemberRequestDto().toEntity(new BCryptPasswordEncoder());
        savedMember.saveRefreshToken("test_refresh_token");

        Long fakeMemberId = 1L;
        ReflectionTestUtils.setField(savedMember, "id", fakeMemberId);
    }

    @Test
    void 회원가입() {
        given(userMemberRepository.save(any())).willReturn(savedMember);

        UserMemberSignupRequestDto memberRequestDto = createUserMemberRequestDto();
        Long savedId = userMemberService.memberJoin(memberRequestDto);

        assertThat(savedId).isEqualTo(1L);
    }

    @Test
    void 중복회원_회원가입_예외() {
        given(memberRepository.existsByEmail("abc@naver.com")).willReturn(true);

        assertThatThrownBy(
                () -> userMemberService.memberJoin(createUserMemberRequestDto())
        ).isInstanceOf(ExistMemberException.class);
    }

    @Test
    void 로그인() throws Exception {
        // TODO: 2022-06-25 작성 보류..

        MemberLoginResponseDto loginResponseDto = userMemberService.login(MemberLoginRequestDto.builder()
                .email("abc@naver.com")
                .password("1234")
                .build());

        assertThat(loginResponseDto.getAccessToken()).isEqualTo("test_access_toke");
        assertThat(loginResponseDto.getRefreshToken()).isEqualTo("test_refresh_token");
    }

    private List<GrantedAuthority> mapToAuthorities(List<MemberRoleEnum> memberRoleEnums) {
        return memberRoleEnums.stream()
                .map(memberRole -> new SimpleGrantedAuthority(memberRole.toString()))
                .collect(Collectors.toList());
    }

    @Test
    void 토큰_재발급() {
        given(jwtProvider.validateToken(any())).willReturn(true);
        given(jwtProvider.getAuthentication(any())).willReturn(new UsernamePasswordAuthenticationToken(savedMember.getEmail(),
                savedMember.getPassword(),
                mapToAuthorities(Arrays.asList(MemberRoleEnum.ROLE_USER))));

        given(memberRepository.findByEmailWithRefreshToken(any())).willReturn(Optional.ofNullable(savedMember));
        given(jwtProvider.createToken(any())).willReturn(MemberLoginResponseDto.builder()
                .accessToken("new_test_access_toke")
                .refreshToken("new_test_refresh_token")
                .build());

        MemberLoginResponseDto loginResponseDto = userMemberService.reissue(MemberReissueRequestDto.builder()
                .accessToken("test_access_toke")
                .refreshToken("test_refresh_token")
                .build());

        assertThat(loginResponseDto.getAccessToken()).isEqualTo("new_test_access_toke");
        assertThat(loginResponseDto.getRefreshToken()).isEqualTo("new_test_refresh_token");
    }

    private UserMemberSignupRequestDto createUserMemberRequestDto() {
        return UserMemberSignupRequestDto.builder()
                .name("홍길동")
                .email("abc@naver.com")
                .password("1234")
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