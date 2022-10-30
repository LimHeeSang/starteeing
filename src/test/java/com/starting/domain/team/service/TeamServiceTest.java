package com.starting.domain.team.service;

import com.starting.domain.member.entity.UserMember;
import com.starting.domain.member.repository.MemberRepository;
import com.starting.domain.team.dto.TeamCreateRequestDto;
import com.starting.domain.team.entity.Team;
import com.starting.domain.team.entity.TeamUserMember;
import com.starting.domain.team.repository.TeamRepository;
import com.starting.domain.team.repository.TeamUserMemberRepository;
import com.starting.test.TestUserMemberFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

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

    Long userMemberId1;
    Long userMemberId2;
    Long userMemberId3;
    Long testTeamId;

    @BeforeEach
    void setUp() {
        member1 = TestUserMemberFactory.create();
        member2 = TestUserMemberFactory.create();
        member3 = TestUserMemberFactory.create();
        testTeam = Team.builder()
                .teamName("testTeam")
                .members(List.of(member1, member2, member3))
                .build();

        userMemberId1 = 1L;
        userMemberId2 = 2L;
        userMemberId3 = 3L;
        testTeamId = 4L;

        ReflectionTestUtils.setField(member1, "id", userMemberId1);
        ReflectionTestUtils.setField(member2, "id", userMemberId2);
        ReflectionTestUtils.setField(member3, "id", userMemberId3);
        ReflectionTestUtils.setField(testTeam, "id", testTeamId);
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
        given(teamUserMemberRepository.findByTeamAndUserMember(any(), any())).willReturn(Optional.of(createTeamUserMember()));

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
        TeamCreateRequestDto createRequestDto = TeamCreateRequestDto.builder()
                .teamName("테스트 팀")
                .memberIds(List.of(String.valueOf(userMemberId2), String.valueOf(userMemberId3), String.valueOf(userMemberId1))).build();
        return createRequestDto;
    }
}