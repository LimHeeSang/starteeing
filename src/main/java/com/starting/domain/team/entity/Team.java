package com.starting.domain.team.entity;

import com.starting.domain.common.BaseTimeEntity;
import com.starting.domain.member.entity.GenderEnum;
import com.starting.domain.meeting.entity.TeamMatches;
import com.starting.domain.meeting.exception.NotEqualGenderException;
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
public class Team extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TeamUserMember> members = new ArrayList<>();

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<TeamMatches> matches = new ArrayList<>();

    @Builder
    public Team(String teamName, List<Member> members) {
        this.name = teamName;

        List<TeamUserMember> teamUserMembers = members.stream()
                .map(member -> TeamUserMember.builder()
                        .team(this)
                        .userMember((UserMember) member)
                        .build()).collect(Collectors.toList());

        teamUserMembers.forEach(this::addUserMember);
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

    public void addTeamMatch(TeamMatches teamMatches) {
        matches.add(teamMatches);
    }

    public int getUserMemberCount() {
        return members.size();
    }

    public boolean isNotEqualMembersGender() {
        List<GenderEnum> genders = members.stream().map(teamUserMember -> teamUserMember.getUserMember().getGenderEnum()).collect(Collectors.toList());

        GenderEnum criterion = genders.get(0);
        return genders.stream().noneMatch(g -> g.equals(criterion));
    }

    private GenderEnum isEqualMembersGender() {
        List<GenderEnum> genders = members.stream().map(teamUserMember -> teamUserMember.getUserMember().getGenderEnum()).collect(Collectors.toList());

        GenderEnum criterion = genders.get(0);
        if (genders.stream().allMatch(g -> g.equals(criterion))) {
            return criterion;
        }
        throw new NotEqualGenderException();
    }

    public GenderEnum getTeamGender() {
        return isEqualMembersGender();
    }
}