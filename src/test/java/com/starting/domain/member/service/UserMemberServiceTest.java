package com.starting.domain.member.service;

import com.starting.domain.member.dto.*;
import com.starting.domain.member.entity.MemberRoleEnum;
import com.starting.domain.member.entity.UserMember;
import com.starting.domain.member.exception.ExistMemberException;
import com.starting.domain.member.repository.MemberRepository;
import com.starting.domain.member.repository.UserMemberRepository;
import com.starting.global.security.JwtAuthenticationProvider;
import com.starting.global.security.JwtProvider;
import com.starting.global.security.UserDetailsServiceImpl;
import com.starting.test.TestUserMemberFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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
    @Mock
    AuthenticationManagerBuilder authenticationManagerBuilder;

    @InjectMocks
    UserMemberService userMemberService;

    UserMember savedMember;
    UserMember savedMember2;

    @BeforeEach
    void setUp() {
        savedMember = TestUserMemberFactory.create();

        savedMember2 = TestUserMemberFactory.create();

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
        given(memberRepository.existsByEmail(any())).willReturn(true);

        assertThatThrownBy(
                () -> userMemberService.memberJoin(createUserMemberRequestDto())
        ).isInstanceOf(ExistMemberException.class);
    }

    void 로그인() throws Exception {
        //authenticationManagerBuilder.getObject() test에서 null문제 해결해야함..
        given(authenticationManagerBuilder.getObject().authenticate(any())).willReturn(new UsernamePasswordAuthenticationToken(
                        savedMember.getEmail(),
                        savedMember.getPassword(),
                        mapToAuthorities(Arrays.asList(MemberRoleEnum.ROLE_USER))));
        given(memberRepository.findByEmailWithRefreshToken(any())).willReturn(Optional.ofNullable(savedMember));

        MemberLoginResponseDto loginResponseDto = userMemberService.login(MemberLoginRequestDto.builder()
                .email(savedMember.getEmail())
                .password(savedMember.getPassword())
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
    void 토큰_재발급_Oauth로_회원가입() {
        given(jwtProvider.validateToken(any())).willReturn(true);
        given(jwtProvider.getAuthentication(any())).willReturn(new UsernamePasswordAuthenticationToken(
                savedMember.getUserId(),
                savedMember.getPassword(),
                mapToAuthorities(Arrays.asList(MemberRoleEnum.ROLE_USER))));

        given(memberRepository.findByUserIdWithRefreshToken(any())).willReturn(Optional.ofNullable(savedMember));
        given(jwtProvider.createToken(any())).willReturn(MemberLoginResponseDto.builder()
                .accessToken("new_test_access_toke")
                .refreshToken("new_test_refresh_token")
                .build());

        MemberLoginResponseDto loginResponseDto = userMemberService.reissue(MemberReissueRequestDto.builder()
                .accessToken("Test_Access_Token_Value")
                .refreshToken("Test_Refresh_Token_Value")
                .build());

        assertThat(loginResponseDto.getAccessToken()).isEqualTo("new_test_access_toke");
        assertThat(loginResponseDto.getRefreshToken()).isEqualTo("new_test_refresh_token");
    }

    @Test
    void 토큰_재발급_Email로_회원가입() {
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
                .accessToken("Test_Access_Token_Value")
                .refreshToken("Test_Refresh_Token_Value")
                .build());

        assertThat(loginResponseDto.getAccessToken()).isEqualTo("new_test_access_toke");
        assertThat(loginResponseDto.getRefreshToken()).isEqualTo("new_test_refresh_token");
    }

    private InputUserDataRequestDto createInputUserDateRequestDto() {
        return InputUserDataRequestDto.builder()
                .name("이름")
                .birthOfDate("1998-09-04")
                .phoneNumber("010-1234-1234")
                .nickname("닉네임")
                .mbti("estj")
                .school("학교")
                .department("학과")
                .uniqSchoolNumber("학번")
                .build();
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