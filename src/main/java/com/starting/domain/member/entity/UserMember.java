package com.starting.domain.member.entity;

import com.starting.domain.friends.entity.Friend;
import com.starting.domain.friends.entity.FriendStatus;
import com.starting.domain.member.dto.InputUserDataRequestDto;
import com.starting.domain.team.entity.TeamUserMember;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Getter
@Entity
public class UserMember extends Member {

    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd";

    @Column(unique = true)
    private String nickName;

    @Enumerated(EnumType.STRING)
    private GenderEnum genderEnum;

    private LocalDate birthOfDate;

    @Column(unique = true)
    private String phoneNumber;

    private String mbti;

    private double temperature;

    private boolean isInputUserData;

    @OneToMany(mappedBy = "userMember", cascade = CascadeType.ALL)
    private final List<Friend> friends = new ArrayList<>();

    @Embedded
    private SchoolInfo schoolInfo;

    @OneToMany(mappedBy = "userMember", cascade = CascadeType.ALL)
    private final List<TeamUserMember> teams = new ArrayList<>();

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
                .friendStatus(FriendStatus.REQUEST)
                .build();
        friends.add(friend);

        responseFriend(userMember);
    }

    private void responseFriend(UserMember userMember) {
        Friend friend = Friend.builder()
                .friendId(this.getId())
                .userMember(userMember)
                .friendStatus(FriendStatus.RESPONSE)
                .build();

        userMember.friends.add(friend);
    }

    public boolean isMale() {
        return genderEnum.isMale();
    }

    public boolean isFemale() {
        return genderEnum.isFemale();
    }

    public GenderEnum getGenderEnum() {
        return genderEnum;
    }

    public boolean isInputUserData() {
        return isInputUserData;
    }

    @Override
    public void inputUserData(InputUserDataRequestDto requestDto) {
        super.inputUserData(requestDto);
        this.birthOfDate = changeBirthOfDate(requestDto.getBirthOfDate());
        this.phoneNumber = requestDto.getPhoneNumber();
        this.nickName = requestDto.getNickname();
        this.mbti = requestDto.getMbti();
        this.schoolInfo = createSchoolInfo(
                requestDto.getSchool(),
                requestDto.getDepartment(),
                requestDto.getUniqSchoolNumber()
        );

        changeInputUserDataFlag();
    }

    private SchoolInfo createSchoolInfo(String school, String department, String uniqSchoolNumber) {
        return SchoolInfo.builder()
                .school(school)
                .department(department)
                .uniqSchoolNumber(uniqSchoolNumber)
                .build();
    }

    private LocalDate changeBirthOfDate(String birthOfDate) {
        return LocalDate.parse(birthOfDate, DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
    }

    private void changeInputUserDataFlag() {
        this.isInputUserData = !isInputUserData;
    }
}