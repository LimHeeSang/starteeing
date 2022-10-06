package com.starting.domain.member.repository;

import com.starting.domain.member.entity.UserMember;
import com.starting.test.TestUserMemberFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserMemberRepositoryTest {

    @Autowired
    private UserMemberRepository userMemberRepository;

    UserMember testMember1;
    UserMember testMember2;
    UserMember testMember3;

    @BeforeEach
    void setUp() {
        testMember1 = TestUserMemberFactory.create();
        testMember2 = TestUserMemberFactory.create();
        testMember3 = TestUserMemberFactory.create();

        userMemberRepository.save(testMember1);
        userMemberRepository.save(testMember2);
        userMemberRepository.save(testMember3);
    }

    @Test
    void findByNickName() {
        UserMember findMember = userMemberRepository.findByNickName(testMember1.getNickName()).get();

        assertThat(findMember).isEqualTo(testMember1);
        assertThat(findMember.getId()).isEqualTo(testMember1.getId());
    }

    @Test
    void findNicknamesByIdList() {
        List<Long> ids = Arrays.asList(testMember1.getId(), testMember3.getId());

        List<String> nicknames = userMemberRepository.findNicknamesByIdList(ids);
        assertThat(nicknames).contains(testMember1.getNickName(), testMember3.getNickName());
    }

    @Test
    void existByNickName() {
        boolean result1 = userMemberRepository.existsByNickName(testMember1.getNickName());
        boolean result2 = userMemberRepository.existsByNickName("nothing nickname");

        assertThat(result1).isTrue();
        assertThat(result2).isFalse();
    }
}