package com.starting.domain.member.dto;

import com.starting.domain.member.entity.SchoolInfo;
import com.starting.domain.member.entity.UserMember;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserMemberInfoResponseDto {

    private final Long memberId;

    private final String name;

    private final String nickname;

    private final String imageProfileUrl;

    private final String gender;

    private final String mbti;

    private final String school;

    private final String department;

    private final String uniqSchoolNumber;

    private final double temperature;

    public static UserMemberInfoResponseDto of(UserMember userMember) {
        return UserMemberInfoResponseDto.builder()
                .memberId(userMember.getId())
                .name(userMember.getName())
                .nickname(userMember.getNickName())

                .imageProfileUrl(userMember.getImageProfileUrl())
                .gender(userMember.getGenderEnum().name())
                .mbti(userMember.getMbti())

                .school(getUserSchoolInfo(userMember).getSchool())
                .department(getUserSchoolInfo(userMember).getDepartment())
                .uniqSchoolNumber(getUserSchoolInfo(userMember).getUniqSchoolNumber())

                .temperature(userMember.getTemperature())
                .build();
    }

    private static SchoolInfo getUserSchoolInfo(UserMember userMember) {
        return userMember.getSchoolInfo();
    }
}
