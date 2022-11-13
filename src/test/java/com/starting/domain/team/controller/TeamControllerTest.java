package com.starting.domain.team.controller;

import com.starting.domain.member.entity.UserMember;
import com.starting.domain.team.dto.TeamResponseDtos;
import com.starting.domain.team.entity.Team;
import com.starting.domain.team.entity.TeamUserMember;
import com.starting.domain.team.service.TeamService;
import com.starting.global.exception.common.CommonExEnum;
import com.starting.global.response.ResponseService;
import com.starting.global.response.result.SingleResult;
import com.starting.global.security.JwtAuthenticationFilter;
import com.starting.global.security.SecurityConfig;
import com.starting.test.TestUserMemberFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(controllers = TeamController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthenticationFilter.class),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)})
class TeamControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TeamService teamService;
    @MockBean
    ResponseService responseService;

    UserMember member1;
    Team testTeam;
    Team testTeam2;

    Long userMemberId1;
    Long testTeamId;
    Long testTeamId2;

    @BeforeEach
    void setUp() {
        member1 = TestUserMemberFactory.create();
        testTeam = Team.builder()
                .teamName("testTeam")
                .members(List.of(member1))
                .build();
        testTeam2 = Team.builder()
                .teamName("testTeam2")
                .members(List.of(member1))
                .build();

        userMemberId1 = 1L;
        testTeamId = 4L;
        testTeamId2 = 5L;
        ReflectionTestUtils.setField(member1, "id", userMemberId1);
        ReflectionTestUtils.setField(testTeam, "id", testTeamId);
        ReflectionTestUtils.setField(testTeam2, "id", testTeamId2);
    }

    @Test
    void getTeamList() throws Exception {
        given(teamService.getTeamList(any())).willReturn(createTeamResponseDtos());
        given(responseService.getSingleResult(any()))
                .willReturn(SingleResult.createSingleResult(createTeamResponseDtos().getResult()));

        mockMvc.perform(get("/team/1")
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(CommonExEnum.SUCCESS.getCode())))
                .andExpect(jsonPath("$.message", is(CommonExEnum.SUCCESS.getMessage())))
                .andExpect(jsonPath("$.data[0].teamId", is(4)))
                .andExpect(jsonPath("$.data[0].teamName", is("testTeam")))
                .andExpect(jsonPath("$.data[1].teamId", is(5)))
                .andExpect(jsonPath("$.data[1].teamName", is("testTeam2")));
    }

    private TeamResponseDtos createTeamResponseDtos() {
        return new TeamResponseDtos(List.of(
                TeamUserMember.builder().userMember(member1).team(testTeam).build(),
                TeamUserMember.builder().userMember(member1).team(testTeam2).build()
        ));
    }
}