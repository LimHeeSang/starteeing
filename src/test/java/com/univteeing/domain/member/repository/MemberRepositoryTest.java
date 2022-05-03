package com.univteeing.domain.member.repository;

import com.univteeing.domain.member.entity.Member;
import com.univteeing.domain.member.entity.MemberRole;
import com.univteeing.domain.member.entity.SchoolInfo;
import com.univteeing.domain.member.entity.UserMember;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest {

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

        memberRepository.save(member);

        Member findMember = memberRepository.findById(member.getId()).get();
        assertThat(findMember).isEqualTo(member);
    }
}