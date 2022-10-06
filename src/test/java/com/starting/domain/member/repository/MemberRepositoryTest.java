package com.starting.domain.member.repository;

import com.starting.domain.member.entity.*;
import com.starting.test.TestUserMemberFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private UserMemberRepository userMemberRepository;

    private UserMember testUserMember;
    private UserMember testUserMember2;

    @BeforeEach
    void setUp() {
        testUserMember = TestUserMemberFactory.create();
        testUserMember2 = TestUserMemberFactory.create();

        userMemberRepository.save(testUserMember);
        userMemberRepository.save(testUserMember2);
    }

    @Test
    void existsByEmail() {
        boolean result = memberRepository.existsByEmail(testUserMember.getEmail());
        assertThat(result).isTrue();
    }

    @Test
    void findByEmail() {
        Member member = memberRepository.findByEmail(testUserMember.getEmail()).get();

        assertThat(member.getEmail()).isEqualTo(testUserMember.getEmail());
        assertThat(member.getName()).isEqualTo(testUserMember.getName());
    }

    @Test
    void findByEmailWithMemberRoles() {
        Member member = memberRepository.findByEmailWithMemberRoles(testUserMember.getEmail()).get();

        assertThat(member.getMemberRoles()).containsOnly(new MemberRole(MemberRoleEnum.ROLE_USER));
    }

    @Test
    void findByEmailWithRefreshToken() {
        Member member = memberRepository.findByEmailWithRefreshToken(testUserMember.getEmail()).get();

        assertThat(member.getRefreshToken().get().getRefreshTokenValue()).isEqualTo(testUserMember.getRefreshToken().get().getRefreshTokenValue());

    }

    @Test
    void findByUserId() {
        Member member = memberRepository.findByUserId(testUserMember.getUserId()).get();

        assertThat(testUserMember).isEqualTo(member);
    }

    @Test
    void findAllById() {
        Long id = testUserMember.getId();
        Long id1 = testUserMember2.getId();

        List<Member> members = memberRepository.findAllById(List.of(id, id1));
        assertThat(members).contains(testUserMember);
    }
}