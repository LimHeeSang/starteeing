package com.starting.domain.team.service;

import com.starting.domain.member.entity.UserMember;
import com.starting.domain.member.repository.MemberRepository;
import com.starting.domain.team.dto.TeamCreateRequestDto;
import com.starting.domain.team.dto.TeamResponseDtos;
import com.starting.domain.team.entity.Team;
import com.starting.domain.team.entity.TeamUserMember;
import com.starting.domain.team.repository.TeamRepository;
import com.starting.domain.team.repository.TeamUserMemberRepository;
import com.starting.test.TestUserMemberFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static com.starting.domain.team.dto.TeamResponseDtos.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TeamServiceTest {

    @InjectMocks
    private TeamService teamService;

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private TeamUserMemberRepository teamUserMemberRepository;

    @Mock
    private MemberRepository memberRepository;

    UserMember member1;
    UserMember member2;
    UserMember member3;

    Team testTeam;
    Team testTeam2;

    Long userMemberId1;
    Long userMemberId2;
    Long userMemberId3;

    Long testTeamId;
    Long testTeamId2;

    @BeforeEach
    void setUp() {
        member1 = TestUserMemberFactory.create();
        member2 = TestUserMemberFactory.create();
        member3 = TestUserMemberFactory.create();
        testTeam = Team.builder()
                .teamName("testTeam")
                .members(List.of(member1, member2, member3))
                .build();
        testTeam2 = Team.builder()
                .teamName("testTeam2")
                .members(List.of(member1))
                .build();

        userMemberId1 = 1L;
        userMemberId2 = 2L;
        userMemberId3 = 3L;
        testTeamId = 4L;
        testTeamId2 = 5L;

        ReflectionTestUtils.setField(member1, "id", userMemberId1);
        ReflectionTestUtils.setField(member2, "id", userMemberId2);
        ReflectionTestUtils.setField(member3, "id", userMemberId3);
        ReflectionTestUtils.setField(testTeam, "id", testTeamId);
        ReflectionTestUtils.setField(testTeam2, "id", testTeamId2);
    }


    @Test
    void createTeam() {
        given(memberRepository.findById(any())).willReturn(Optional.of(member1));
        given(memberRepository.findAllById(any())).willReturn(List.of(member1, member2, member3));

        given(teamRepository.save(any())).willReturn(testTeam);
        Long teamId = teamService.createTeam(userMemberId1, createTeamCreateRequestDto());

        assertThat(teamId).isEqualTo(testTeamId);
    }

    @Test
    void withDrawTeam() {
        given(memberRepository.findById(any())).willReturn(Optional.of(member1));
        given(teamRepository.findByIdWithMembers(any())).willReturn(Optional.of(testTeam));
        given(teamUserMemberRepository.findByTeamAndUserMember(any(), any()))
                .willReturn(Optional.of(createTeamUserMember()));

        teamService.withDrawTeam(userMemberId1, testTeamId);
    }

    @Test
    void addMember() {
        given(memberRepository.findById(any())).willReturn(Optional.of(member1));
        given(teamRepository.findByIdWithMembers(any())).willReturn(Optional.of(testTeam));
        given(memberRepository.findById(any())).willReturn(Optional.of(member2));

        teamService.addTeamMember(userMemberId1, testTeamId, userMemberId2);
    }

    private TeamUserMember createTeamUserMember() {
        return TeamUserMember.builder().team(testTeam).userMember(member1).build();
    }

    private TeamCreateRequestDto createTeamCreateRequestDto() {
        return TeamCreateRequestDto.builder()
                .teamName("테스트 팀")
                .memberIds(List.of(
                        String.valueOf(userMemberId2),
                        String.valueOf(userMemberId3),
                        String.valueOf(userMemberId1))
                ).build();
    }

    @CsvSource(value = {"0,testTeam", "1,testTeam2"}, delimiter = ',')
    @ParameterizedTest
    void getTeamList(int index, String teamName) {
        given(memberRepository.findById(any())).willReturn(Optional.of(member1));
        given(teamUserMemberRepository.findAllByUserMember(any())).willReturn(createTestTeamUserMember());

        TeamResponseDtos responseDto = teamService.getTeamList(userMemberId1);
        List<TeamResponseDto> result = responseDto.getResult();

        assertThat(result.get(index).getTeamName()).isEqualTo(teamName);
    }

    private List<TeamUserMember> createTestTeamUserMember() {
        return List.of(
                TeamUserMember.builder().userMember(member1).team(testTeam).build(),
                TeamUserMember.builder().userMember(member1).team(testTeam2).build()
        );
    }
}