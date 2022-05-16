package com.starteeing.domain.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.starteeing.domain.member.dto.UserMemberRequestDto;
import com.starteeing.domain.member.entity.MemberRole;
import com.starteeing.domain.member.exception.ExistMemberException;
import com.starteeing.domain.member.exception.MemberExEnum;
import com.starteeing.domain.member.service.UserMemberService;
import com.starteeing.golbal.exception.common.CommonExEnum;
import com.starteeing.golbal.response.ResponseService;
import com.starteeing.golbal.response.result.CommonResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class UserMemberControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;

    @MockBean
    UserMemberService userMemberService;
    @MockBean
    ResponseService responseService;

    @Test
    void signup() throws Exception {
        String body = mapper.writeValueAsString(createUserMemberRequestDto());

        given(userMemberService.memberJoin(createUserMemberRequestDto())).willReturn(1L);
        given(responseService.getSuccessResult()).willReturn(CommonResult.createSuccessResult());

        mockMvc.perform(post("/members")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(CommonExEnum.SUCCESS.getCode())))
                .andExpect(jsonPath("$.message", is(CommonExEnum.SUCCESS.getMessage())));
    }

    @Test
    void handle_ExistMemberException() throws Exception {
        String body = mapper.writeValueAsString(createUserMemberRequestDto());

        given(userMemberService.memberJoin(any())).willThrow(new ExistMemberException());
        given(responseService.getErrorResult(MemberExEnum.ALREADY_EXIST_MEMBER))
                .willReturn(CommonResult.createErrorResult(MemberExEnum.ALREADY_EXIST_MEMBER));

        mockMvc.perform(post("/members")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(MemberExEnum.ALREADY_EXIST_MEMBER.getCode())))
                .andExpect(jsonPath("$.message", is(MemberExEnum.ALREADY_EXIST_MEMBER.getMessage())));
    }

    @Test
    void handle_BindException() throws Exception {
        String body = mapper.writeValueAsString(createWrongUserMemberDto());

        given(responseService.getErrorResult(CommonExEnum.INVALID_BINGING_VALUE))
                .willReturn(CommonResult.createErrorResult(CommonExEnum.INVALID_BINGING_VALUE));

        mockMvc.perform(post("/members")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    private UserMemberRequestDto createUserMemberRequestDto() {
        return UserMemberRequestDto.builder()
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
    }

    private UserMemberRequestDto createWrongUserMemberDto() {
        return UserMemberRequestDto.builder()
                .name("qweqwe09042")
                .email("")
                .memberRole(MemberRole.ROLE_USER)
                .nickname("")
                .birthOfDate(LocalDate.of(1998, 9, 4))
                .phoneNumber("")
                .mbti("")
                .school("")
                .department("")
                .uniqSchoolNumber("")
                .build();
    }
}