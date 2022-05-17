package com.starteeing.domain.member.repository;

import com.starteeing.domain.member.entity.MemberRole;
import com.starteeing.domain.member.entity.SchoolInfo;
import com.starteeing.domain.member.entity.UserMember;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserMemberRepositoryTest {

    @Autowired
    private UserMemberRepository userMemberRepository;

    UserMember member1;
    UserMember member2;
    UserMember member3;

    @BeforeEach
    void setUp() {
        member1 = createUserMember("aaa@naver.com", "userA", "010-1234-0000", "12340000");
        member2 = createUserMember("bbb@naver.com", "userB", "010-1234-0001", "12340001");
        member3 = createUserMember("ccc@naver.com", "userC", "010-1234-0002", "12340002");

        userMemberRepository.save(member1);
        userMemberRepository.save(member2);
        userMemberRepository.save(member3);
    }

    @Test
    void findByNickName() {
        UserMember findMember = userMemberRepository.findByNickName("userB").get();

        assertThat(findMember).isEqualTo(member2);
        assertThat(findMember.getId()).isEqualTo(member2.getId());
    }

    @Test
    void findNicknamesByIdList() {
        List<Long> ids = Arrays.asList(member1.getId(), member3.getId());

        List<String> nicknames = userMemberRepository.findNicknamesByIdList(ids);
        assertThat(nicknames).contains("userA", "userC");
    }

    private UserMember createUserMember(String email, String nickName, String phoneNumber, String schoolNumber) {
        SchoolInfo schoolInfo = SchoolInfo.builder()
                .school("순천향대")
                .department("정보보호학과")
                .uniqSchoolNumber(schoolNumber)
                .build();

        return UserMember.builder()
                .name("홍길동")
                .email(email)
                .memberRole(MemberRole.ROLE_USER)
                .nickName(nickName)
                .birthOfDate(LocalDate.of(1998, 9, 4))
                .phoneNumber(phoneNumber)
                .mbti("estj")
                .temperature(37.5D)
                .schoolInfo(schoolInfo)
                .build();
    }
}