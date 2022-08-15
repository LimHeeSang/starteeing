package com.starting.domain.meeting.entity;

import com.starting.domain.common.BaseTimeEntity;
import com.starting.domain.team.entity.Team;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Getter
@SuperBuilder
@NoArgsConstructor
@Entity
public class TeamMatch extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_match_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id")
    private Match match;

    public TeamMatch(Team team, Match match) {
        this.team = team;
        this.match = match;
        team.addTeamMatch(this);
    }
}