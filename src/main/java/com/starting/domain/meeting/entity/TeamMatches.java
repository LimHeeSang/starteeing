package com.starting.domain.meeting.entity;

import com.starting.domain.common.BaseTimeEntity;
import com.starting.domain.team.entity.Team;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class TeamMatches extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_matches_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "matches_id")
    private Matches matches;

    @Builder
    public TeamMatches(Team team, Matches matches) {
        this.team = team;
        this.matches = matches;
        team.addTeamMatch(this);
    }
}