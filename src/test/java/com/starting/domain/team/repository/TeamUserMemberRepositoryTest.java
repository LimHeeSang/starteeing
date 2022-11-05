package com.starting.domain.team.repository;

import com.starting.domain.member.entity.Member;
import com.starting.domain.member.entity.UserMember;
import com.starting.domain.team.entity.Team;
import com.starting.domain.team.entity.TeamUserMember;
import com.starting.test.TestUserMemberFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TeamUserMemberRepositoryTest {

    @Autowired
    TeamUserMemberRepository teamUserMemberRepository;
    @Autowired
    TeamRepository teamRepository;

    private Team testTeam1;
    private UserMember userMember1;
    private UserMember userMember2;

    @BeforeEach
    void setUp() {
        userMember1 = TestUserMemberFactory.create();
        userMember2 = TestUserMemberFactory.create();
        List<Member> userMembers = List.of(userMember1, userMember2);

        testTeam1 = Team.builder()
                .teamName("TestTeamName1")
                .members(userMembers)
                .build();

        teamRepository.save(testTeam1);
    }

    @Test
    void findByTeamAndUserMember() {
        TeamUserMember teamUserMember = teamUserMemberRepository.findByTeamAndUserMember(testTeam1, userMember1).get();
        assertThat(teamUserMember.getTeam()).isEqualTo(testTeam1);
        assertThat(teamUserMember.getUserMember()).isEqualTo(userMember1);
    }

}