package com.starting.domain.team.entity;

import com.starting.domain.member.entity.Member;
import com.starting.domain.member.entity.UserMember;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long id;

    private String name;

    private Long score;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TeamUserMember> members = new ArrayList<>();

    @Builder
    public Team(String teamName, List<Member> members) {
        this.name = teamName;

        List<TeamUserMember> teamUserMembers = members.stream()
                .map(member -> TeamUserMember.builder()
                .team(this)
                .userMember((UserMember) member)
                .build())
                .collect(Collectors.toList());

        teamUserMembers.stream()
                .forEach(teamUserMember -> this.addUserMember(teamUserMember));
    }

    private void addUserMember(TeamUserMember teamUserMember) {
        members.add(teamUserMember);
    }

    public Long getId() {
        return id;
    }

    public void withDrawMember(TeamUserMember teamUserMember) {
        members.remove(teamUserMember);
    }

    public void addTeamMember(Member member) {
        TeamUserMember teamUserMember = TeamUserMember.builder()
                .team(this)
                .userMember((UserMember) member)
                .build();
        addUserMember(teamUserMember);
    }
}