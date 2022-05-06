package com.univteeing.domain.member.entity;

import com.univteeing.domain.freinds.entity.Friend;
import com.univteeing.domain.freinds.entity.FriendStatus;
import com.univteeing.domain.member.dto.UserMemberRequestDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Entity
public class UserMember extends Member{

    @Column(unique = true)
    private String nickName;

    private LocalDate birthOfDate;

    @Column(unique = true)
    private String phoneNumber;

    private String mbti;

    private double temperature;

    @OneToMany(mappedBy = "userMember", cascade = CascadeType.ALL)
    private final List<Friend> friends = new ArrayList<>();

    @Embedded
    private SchoolInfo schoolInfo;

    public UserMember(UserMemberRequestDto requestDto) {
        this.nickName = requestDto.getNickname();
        this.birthOfDate = requestDto.getBirthOfDate();
        this.phoneNumber = requestDto.getPhoneNumber();
        this.temperature = 37.5D;
        this.mbti = requestDto.getMbti();
        this.schoolInfo = createSchoolInfo(requestDto);
    }

    private SchoolInfo createSchoolInfo(UserMemberRequestDto requestDto) {
        return SchoolInfo.builder()
                .school(requestDto.getSchool())
                .department(requestDto.getDepartment())
                .uniqSchoolNumber(requestDto.getUniqSchoolNumber())
                .build();
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

        friends.add(friend2);
    }
}