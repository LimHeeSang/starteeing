package com.univteeing.domain.member.repository;

import com.univteeing.domain.member.entity.Member;
import com.univteeing.domain.member.entity.MemberRole;
import com.univteeing.domain.member.entity.SchoolInfo;
import com.univteeing.domain.member.entity.UserMember;
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

    @Test
    void name() {
        UserMember member1 = UserMember.builder()
                .email("a").build();

        UserMember member2 = UserMember.builder()
                .email("b").build();

        UserMember member3 = UserMember.builder()
                .email("c").build();

        userMemberRepository.save(member1);
        userMemberRepository.save(member2);
        userMemberRepository.save(member3);

        System.out.println(memberRepository.existsByEmail("e"));
    }
}