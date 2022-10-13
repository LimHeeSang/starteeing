package com.starting.domain.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.starting.domain.member.dto.*;
import com.starting.domain.member.exception.ExistMemberException;
import com.starting.domain.member.exception.MemberExEnum;
import com.starting.domain.member.service.UserMemberService;
import com.starting.global.exception.common.CommonExEnum;
import com.starting.global.response.ResponseService;
import com.starting.global.response.result.CommonResult;
import com.starting.global.response.result.SingleResult;
import com.starting.global.security.JwtAuthenticationFilter;
import com.starting.global.security.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(controllers = UserMemberController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthenticationFilter.class),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)})
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

        mockMvc.perform(post("/signup")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
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

        mockMvc.perform(post("/signup")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
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

        mockMvc.perform(post("/signup")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(CommonExEnum.INVALID_BINGING_VALUE.getCode())))
                .andExpect(jsonPath("$.message", is(CommonExEnum.INVALID_BINGING_VALUE.getMessage())));
    }

    @Test
    void login() throws Exception {
        // TODO: 2022-06-27 작성 보류... 
        String body = mapper.writeValueAsString(createMemberLoginRequestDto());

        given(userMemberService.login(any()))
                .willReturn(MemberLoginResponseDto.builder()
                        .accessToken("test_access_token")
                        .refreshToken("test_refresh_token")
                        .build());
        given(responseService.getSingleResult(any()))
                .willReturn(SingleResult.createSingleResult(MemberLoginResponseDto.builder()
                        .accessToken("test_access_token")
                        .refreshToken("test_refresh_token")
                        .build()));

        mockMvc.perform(post("/login")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(CommonExEnum.SUCCESS.getCode())))
                .andExpect(jsonPath("$.message", is(CommonExEnum.SUCCESS.getMessage())));
    }

    @Test
    void reissue() throws Exception {
        String body = mapper.writeValueAsString(MemberReissueRequestDto.builder().build());

        given(userMemberService.reissue(any()))
                .willReturn(MemberLoginResponseDto.builder().build());
        given(responseService.getSingleResult(any()))
                .willReturn(SingleResult.createSingleResult(MemberLoginResponseDto.builder()
                        .accessToken("new_test_access_token")
                        .refreshToken("new_test_refresh_token")
                        .build()));

        mockMvc.perform(post("/reissue")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(CommonExEnum.SUCCESS.getCode())))
                .andExpect(jsonPath("$.message", is(CommonExEnum.SUCCESS.getMessage())))
                .andExpect(jsonPath("$.data.accessToken", is("new_test_access_token")))
                .andExpect(jsonPath("$.data.refreshToken", is("new_test_refresh_token")));
    }

    @Test
    void isInputUserData_true() throws Exception {
        given(userMemberService.isInputUserData(any())).willReturn(true);
        given(responseService.getSingleResult(any())).willReturn(SingleResult.createSingleResult(true));

        mockMvc.perform(get("/inputted/1")
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(CommonExEnum.SUCCESS.getCode())))
                .andExpect(jsonPath("$.message", is(CommonExEnum.SUCCESS.getMessage())))
                .andExpect(jsonPath("$.data", is(true)));
    }

    @Test
    void isInputUserData_false() throws Exception {
        given(userMemberService.isInputUserData(any())).willReturn(false);
        given(responseService.getSingleResult(any())).willReturn(SingleResult.createSingleResult(false));

        mockMvc.perform(get("/inputted/1")
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(CommonExEnum.SUCCESS.getCode())))
                .andExpect(jsonPath("$.message", is(CommonExEnum.SUCCESS.getMessage())))
                .andExpect(jsonPath("$.data", is(false)));
    }

    @Test
    void inputUserData() throws Exception {
        String body = mapper.writeValueAsString(InputUserDataRequestDto.builder().build());
        given(responseService.getSuccessResult()).willReturn(CommonResult.createSuccessResult());

        mockMvc.perform(post("/inputs/1")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(CommonExEnum.SUCCESS.getCode())))
                .andExpect(jsonPath("$.message", is(CommonExEnum.SUCCESS.getMessage())));
    }

    @Test
    void isDuplicateNickname_true() throws Exception {
        given(userMemberService.isDuplicateNickname(any(), any())).willReturn(true);
        given(responseService.getSingleResult(any())).willReturn(SingleResult.createSingleResult(true));

        mockMvc.perform(get("/duplicate/1/pobi")
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(CommonExEnum.SUCCESS.getCode())))
                .andExpect(jsonPath("$.message", is(CommonExEnum.SUCCESS.getMessage())))
                .andExpect(jsonPath("$.data", is(true)));
    }

    @Test
    void isDuplicateNickname_false() throws Exception {
        given(userMemberService.isDuplicateNickname(any(), any())).willReturn(false);
        given(responseService.getSingleResult(any())).willReturn(SingleResult.createSingleResult(false));

        mockMvc.perform(get("/duplicate/1/pobi")
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(CommonExEnum.SUCCESS.getCode())))
                .andExpect(jsonPath("$.message", is(CommonExEnum.SUCCESS.getMessage())))
                .andExpect(jsonPath("$.data", is(false)));
    }

    @Test
    void getUserMemberInfo() throws Exception {
        given(userMemberService.getUserMemberInfo(any())).willReturn(UserMemberInfoResponseDto.builder().build());
        given(responseService.getSingleResult(any()))
                .willReturn(SingleResult.createSingleResult(UserMemberInfoResponseDto.builder().memberId(1L).build()));

        mockMvc.perform(get("/mypage/1")
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(CommonExEnum.SUCCESS.getCode())))
                .andExpect(jsonPath("$.message", is(CommonExEnum.SUCCESS.getMessage())))
                .andExpect(jsonPath("$.data.memberId", is(1)));
    }

    private UserMemberSignupRequestDto createUserMemberRequestDto() {
        return UserMemberSignupRequestDto.builder()
                .name("홍길동")
                .email("abc@naver.com")
                .password("1234")
                .nickname("길동이")
                .birthOfDate("1998-09-04")
                .phoneNumber("010-8543-0619")
                .mbti("estj")
                .school("순천향대")
                .department("정보보호학과")
                .uniqSchoolNumber("12345678")
                .build();
    }

    private UserMemberSignupRequestDto createWrongUserMemberDto() {
        return UserMemberSignupRequestDto.builder()
                .name("qweqwe09042")
                .password("1234")
                .nickname("")
                .birthOfDate("1998-09-04")
                .phoneNumber("")
                .mbti("")
                .school("")
                .department("")
                .uniqSchoolNumber("")
                .build();
    }

    private MemberLoginRequestDto createMemberLoginRequestDto() {
        return MemberLoginRequestDto.builder()
                .email("test@email.com")
                .password("1234")
                .build();
    }
}