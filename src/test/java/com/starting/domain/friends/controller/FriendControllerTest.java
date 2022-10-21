package com.starting.domain.friends.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.starting.domain.friends.dto.FriendListResponseDto;
import com.starting.domain.friends.entity.Friend;
import com.starting.domain.friends.entity.FriendStatus;
import com.starting.domain.friends.service.FriendService;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(controllers = FriendController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthenticationFilter.class),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)})
class FriendControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;

    @MockBean
    FriendService friendService;
    @MockBean
    ResponseService responseService;

    @Test
    void getFriendsList() throws Exception {
        given(friendService.getFriendsList(any())).willReturn(createFriendListResponseDto());
        given(responseService.getSingleResult(any()))
                .willReturn(SingleResult.createSingleResult(createFriendListResponseDto().getResult()));

        mockMvc.perform(get("/friend/all/1")
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(CommonExEnum.SUCCESS.getCode())))
                .andExpect(jsonPath("$.message", is(CommonExEnum.SUCCESS.getMessage())))
                .andExpect(jsonPath("$.data[0].friendId", is(1)))
                .andExpect(jsonPath("$.data[0].nickname", is("test_nickname")));
    }

    @Test
    void getAcceptFriendsList() throws Exception {
        given(friendService.getAcceptFriendsList(any())).willReturn(createFriendListResponseDto());
        given(responseService.getSingleResult(any()))
                .willReturn(SingleResult.createSingleResult(createFriendListResponseDto().getResult()));

        mockMvc.perform(get("/friend/all/accept/1")
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(CommonExEnum.SUCCESS.getCode())))
                .andExpect(jsonPath("$.message", is(CommonExEnum.SUCCESS.getMessage())))
                .andExpect(jsonPath("$.data[0].friendId", is(1)))
                .andExpect(jsonPath("$.data[0].nickname", is("test_nickname")));
    }

    private FriendListResponseDto createFriendListResponseDto() {
        return FriendListResponseDto.builder()
                .friends(List.of(Friend.builder().friendId(1L).friendStatus(FriendStatus.REQUEST).build()))
                .nicknames(List.of("test_nickname")).build();
    }

    @Test
    void requestFriend() throws Exception {
        given(responseService.getSuccessResult()).willReturn(CommonResult.createSuccessResult());

        mockMvc.perform(post("/friend/request/1?nickname=pobi")
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(CommonExEnum.SUCCESS.getCode())))
                .andExpect(jsonPath("$.message", is(CommonExEnum.SUCCESS.getMessage())));
    }

    @Test
    void acceptFriend() throws Exception {
        given(responseService.getSuccessResult()).willReturn(CommonResult.createSuccessResult());

        mockMvc.perform(post("/friend/accept/1?nickname=pobi")
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(CommonExEnum.SUCCESS.getCode())))
                .andExpect(jsonPath("$.message", is(CommonExEnum.SUCCESS.getMessage())));
    }

    @Test
    void rejectFriend() throws Exception {
        given(responseService.getSuccessResult()).willReturn(CommonResult.createSuccessResult());

        mockMvc.perform(post("/friend/reject/1?nickname=pobi")
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(CommonExEnum.SUCCESS.getCode())))
                .andExpect(jsonPath("$.message", is(CommonExEnum.SUCCESS.getMessage())));
    }

    @Test
    void deleteFriend() throws Exception {
        given(responseService.getSuccessResult()).willReturn(CommonResult.createSuccessResult());

        mockMvc.perform(post("/friend/delete/1?nickname=pobi")
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(CommonExEnum.SUCCESS.getCode())))
                .andExpect(jsonPath("$.message", is(CommonExEnum.SUCCESS.getMessage())));
    }
}