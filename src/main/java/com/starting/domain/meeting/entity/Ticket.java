package com.starting.domain.meeting.entity;

import com.starting.domain.common.BaseTimeEntity;
import com.starting.domain.member.entity.UserMember;
import com.starting.domain.team.entity.Team;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Getter
@SuperBuilder
@NoArgsConstructor
@Entity
public class Ticket extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private UserMember userMember;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "team_id")
    private Team team;

    @Enumerated(value = EnumType.STRING)
    private GenderEnum genderEnum;

    private int memberCount;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "box_id")
    private Box box;

    public Ticket(UserMember userMember, Team team) {
        this.userMember = userMember;
        this.team = team;
        decisionGenderEnum(userMember);
        this.memberCount = team.getUserMemberCount();
    }

    private void decisionGenderEnum(UserMember userMember) {
        if (userMember.isMale()) {
            genderEnum = GenderEnum.MALE;
        }
        if (userMember.isFemale()) {
            genderEnum = GenderEnum.FEMALE;
        }
    }
}