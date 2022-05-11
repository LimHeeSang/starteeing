package com.starteeing.domain.member.repository;

import com.starteeing.domain.member.entity.MemberRole;
import com.starteeing.domain.member.entity.SchoolInfo;
import com.starteeing.domain.member.entity.UserMember;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

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
        SchoolInfo schoolInfo = SchoolInfo.builder()
                .school("순천향대")
                .department("정보보호학과")
                .uniqSchoolNumber("12345678")
                .build();

        userMember = UserMember.builder()
                .name("홍길동")
                .email("abc@naver.com")
                .memberRole(MemberRole.ROLE_USER)
                .nickName("길동이")
                .birthOfDate(LocalDate.of(1998, 9, 4))
                .phoneNumber("010-8543-0619")
                .mbti("estj")
                .schoolInfo(schoolInfo)
                .build();

        userMemberRepository.save(userMember);
    }

    @Test
    void existsByEmail() {
        boolean result = memberRepository.existsByEmail("abc@naver.com");
        assertThat(result).isTrue();
    }
}