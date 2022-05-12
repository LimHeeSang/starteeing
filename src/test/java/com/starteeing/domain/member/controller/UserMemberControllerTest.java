package com.starteeing.domain.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.starteeing.domain.member.dto.UserMemberRequestDto;
import com.starteeing.domain.member.entity.MemberRole;
import com.starteeing.golbal.exception.common.CommonExEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserMemberControllerTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper mapper;

    @Test
    void signup() throws Exception {
        String body = mapper.writeValueAsString(createUserMemberRequestDto());

        mvc.perform(post("/members")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(CommonExEnum.SUCCESS.getCode())))
                .andExpect(jsonPath("$.message", is(CommonExEnum.SUCCESS.getMessage())));
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