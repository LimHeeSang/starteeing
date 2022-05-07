package com.starteeing.domain.member.repository;

import com.starteeing.domain.member.entity.Member;
import com.starteeing.domain.member.entity.MemberRole;
import com.starteeing.domain.member.entity.SchoolInfo;
import com.starteeing.domain.member.entity.UserMember;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest {

    @Autowired
    UserMemberRepository userMemberRepository;
    @Autowired
    MemberRepository memberRepository;

    @Test
    void 유저멤버_저장() {
        SchoolInfo schoolInfo = SchoolInfo.builder()
                .school("순천향대학교")
                .department("정보보호학과")
                .uniqSchoolNumber("20174544")
                .build();

        UserMember member = UserMember.builder()
                .name("임희상")
                .email("dlagmltkd12@naver.com")
                .memberRole(MemberRole.ROLE_USER)
                .nickName("heesang")
                .birthOfDate(LocalDate.of(1998, 9, 4))
                .phoneNumber("010-8543-0619")
                .mbti("ESTJ")
                .temperature(36.5D)
                .schoolInfo(schoolInfo)
                .build();

        userMemberRepository.save(member);

        Member findMember = userMemberRepository.findById(member.getId()).get();
        assertThat(findMember).isEqualTo(member);
    }
}