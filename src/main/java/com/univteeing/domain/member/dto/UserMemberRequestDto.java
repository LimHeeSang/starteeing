package com.univteeing.domain.member.dto;

import com.univteeing.domain.member.entity.MemberRole;
import com.univteeing.domain.member.entity.SchoolInfo;
import com.univteeing.domain.member.entity.UserMember;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class UserMemberRequestDto {

    public static final double STANDARD_TEMPERATURE = 37.5D;

    private String name;

    private String email;

    private MemberRole memberRole;

    private String nickname;

    private LocalDate birthOfDate;

    private String phoneNumber;

    private String mbti;

    private String school;

    private String department;

    private String uniqSchoolNumber;

    public UserMember toEntity() {
        return UserMember.builder()
                .name(name)
                .email(email)
                .memberRole(MemberRole.ROLE_USER)
                .nickName(nickname)
                .birthOfDate(birthOfDate)
                .phoneNumber(phoneNumber)
                .mbti(mbti)
                .temperature(STANDARD_TEMPERATURE)
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