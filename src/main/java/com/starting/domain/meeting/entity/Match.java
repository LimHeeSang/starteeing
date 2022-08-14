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
public class Match extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "match_id")
    private Long id;

    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL)
    private List<TeamMatch> teams = new ArrayList<>();

    @Builder
    Match(List<Team> teams) {
        List<TeamMatch> teamMatches = teams.stream()
                .map(team -> TeamMatch.builder()
                        .team(team)
                        .match(this)
                        .build()).collect(Collectors.toList());

        teamMatches.stream().forEach(this::addTeamMatch);
    }

    private void addTeamMatch(TeamMatch teamMatch) {
        teams.add(teamMatch);
    }

    public List<Team> getTeams() {
        return teams.stream().map(TeamMatch::getTeam).collect(Collectors.toList());
    }
}