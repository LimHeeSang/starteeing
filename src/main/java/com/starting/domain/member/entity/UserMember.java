package com.starting.domain.member.entity;

import com.starting.domain.friends.entity.Friend;
import com.starting.domain.friends.entity.FriendStatus;
import com.starting.domain.team.entity.TeamUserMember;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Entity
public class UserMember extends Member {

    @Column(unique = true, nullable = false)
    private String nickName;

    private String gender;

    @Column(nullable = false)
    private LocalDate birthOfDate;

    @Column(unique = true, nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String mbti;

    @Column(nullable = false)
    private double temperature;

    @OneToMany(mappedBy = "userMember", cascade = CascadeType.ALL)
    private final List<Friend> friends = new ArrayList<>();

    @Embedded
    private SchoolInfo schoolInfo;

    @OneToMany(mappedBy = "userMember", cascade = CascadeType.ALL)
    private List<TeamUserMember> teams = new ArrayList<>();

    {
        MemberRole memberRole = MemberRole.builder().memberRoleEnum(MemberRoleEnum.ROLE_USER).member(this).build();
        memberRoles.add(memberRole);
    }

    public List<Friend> getFriends() {
        return friends;
    }

    public void addTeam(TeamUserMember teamUserMember) {
        teams.add(teamUserMember);
    }

    public void requestFriend(UserMember userMember) {
        Friend friend = Friend.builder()
                .friendId(userMember.getId())
                .userMember(this)
                .friendsStatus(FriendStatus.REQUEST)
                .build();
        friends.add(friend);

        responseFriend(userMember);
    }

    private void responseFriend(UserMember userMember) {
        Friend friend2 = Friend.builder()
                .friendId(this.getId())
                .userMember(userMember)
                .friendsStatus(FriendStatus.RESPONSE)
                .build();

        userMember.friends.add(friend2);
    }
}