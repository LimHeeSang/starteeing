package com.starting.domain.meeting.entity;

import com.starting.domain.common.BaseTimeEntity;
import com.starting.domain.team.entity.Team;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Matches extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "matches_id")
    private Long id;

    @OneToMany(mappedBy = "matches", cascade = CascadeType.ALL)
    private final List<TeamMatches> teams = new ArrayList<>();

    @Builder
    public Matches(List<Team> teams) {
        List<TeamMatches> teamMatches = teams.stream()
                .map(team -> TeamMatches.builder()
                        .team(team)
                        .matches(this)
                        .build()).collect(Collectors.toList());

        teamMatches.forEach(this::addTeamMatch);
    }

    private void addTeamMatch(TeamMatches teamMatches) {
        teams.add(teamMatches);
    }

    public List<Team> getTeams() {
        return teams.stream().map(TeamMatches::getTeam).collect(Collectors.toList());
    }
}