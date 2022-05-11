package com.starteeing.domain.member.service;

import com.starteeing.domain.member.dto.UserMemberRequestDto;
import com.starteeing.domain.member.entity.MemberRole;
import com.starteeing.domain.member.exception.ExistMemberException;
import com.starteeing.domain.member.repository.MemberRepository;
import com.starteeing.domain.member.repository.UserMemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserMemberServiceTest {

    @Mock
    UserMemberRepository userMemberRepository;
    @Mock
    MemberRepository memberRepository;
    @InjectMocks
    UserMemberService userMemberService;

    @Test
    void 회원가입() {
        when(userMemberRepository.save(any())).thenReturn(createUserMemberRequestDto().toEntity());

        UserMemberRequestDto memberRequestDto = createUserMemberRequestDto();

        userMemberService.memberJoin(memberRequestDto);
    }

    @Test
    void 중복회원_회원가입_예외() {
        when(memberRepository.existsByEmail("abc@naver.com")).thenReturn(true);

        assertThatThrownBy(
                () -> userMemberService.memberJoin(createUserMemberRequestDto())
        ).isInstanceOf(ExistMemberException.class);
    }

    private UserMemberRequestDto createUserMemberRequestDto() {
        return UserMemberRequestDto.builder()
                .name("홍길동")
                .email("abc@naver.com")
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