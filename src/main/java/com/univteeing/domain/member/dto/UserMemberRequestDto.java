package com.univteeing.domain.member.dto;

import com.univteeing.domain.member.entity.SchoolInfo;
import com.univteeing.domain.member.entity.UserMember;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class UserMemberRequestDto {

    private String nickname;

    private LocalDate birthOfDate;

    private String phoneNumber;

    private String mbti;

    private String school;

    private String department;

    private String uniqSchoolNumber;

    public UserMember toEntity() {
        return UserMember.builder()
                .nickName(nickname)
                .birthOfDate(birthOfDate)
                .phoneNumber(phoneNumber)
                .mbti(mbti)
                .temperature(37.5D)
                .schoolInfo(createSchoolInfo())
                .build();
    }

    private SchoolInfo createSchoolInfo() {
        return SchoolInfo.builder()
                .school(school)
                .department(department)
                .uniqSchoolNumber(uniqSchoolNumber)
                .build();
    }
}