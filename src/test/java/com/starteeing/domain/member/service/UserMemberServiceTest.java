package com.starteeing.domain.member.service;

import com.starteeing.domain.member.dto.UserMemberRequestDto;
import com.starteeing.domain.member.entity.MemberRole;
import com.starteeing.domain.member.repository.UserMemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class UserMemberServiceTest {

    @Autowired
    UserMemberService userMemberService;
    @Autowired
    UserMemberRepository userMemberRepository;

    @Test
    void 회원가입() {
        UserMemberRequestDto memberRequestDto = createUserMemberRequestDto();

        Long savedId = userMemberService.memberJoin(memberRequestDto);

        assertThat(savedId).isEqualTo(userMemberRepository.findById(savedId).get().getId());
    }

    private UserMemberRequestDto createUserMemberRequestDto() {
        UserMemberRequestDto memberRequestDto = UserMemberRequestDto.builder()
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
        return memberRequestDto;
    }
}