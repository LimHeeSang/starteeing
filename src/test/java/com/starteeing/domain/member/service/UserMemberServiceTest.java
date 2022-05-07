package com.starteeing.domain.member.service;

import com.starteeing.domain.member.dto.UserMemberRequestDto;
import com.starteeing.domain.member.repository.UserMemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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
        UserMemberRequestDto memberRequestDto = new UserMemberRequestDto();

        Long savedId = userMemberService.memberJoin(memberRequestDto);

        assertThat(savedId).isEqualTo(userMemberRepository.findById(savedId).get().getId());
    }
}