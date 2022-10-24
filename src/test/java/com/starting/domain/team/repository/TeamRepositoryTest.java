package com.starting.domain.team.repository;

import com.starting.domain.member.entity.Member;
import com.starting.domain.member.entity.UserMember;
import com.starting.domain.team.entity.Team;
import com.starting.test.TestUserMemberFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TeamRepositoryTest {

    @Autowired
    private TeamRepository teamRepository;

    private Team testTeam;
    private UserMember userMember1;
    private UserMember userMember2;


    @BeforeEach
    void setUp() {
        List<Member> userMembers = List.of(TestUserMemberFactory.create(), TestUserMemberFactory.create());

        testTeam = Team.builder()
                .teamName("TestTeamName")
                .members(userMembers)
                .build();

        teamRepository.save(testTeam);
    }

    @Test
    void findByIdWithMembers() {
        Team findTeam = teamRepository.findByIdWithMembers(testTeam.getId()).get();

        assertThat(findTeam.getId()).isEqualTo(testTeam.getId());
        assertThat(findTeam.getName()).isEqualTo("TestTeamName");
        assertThat(findTeam.getMembers().get(0).getUserMember().getName()).isEqualTo("테스트 유저1");
    }
}