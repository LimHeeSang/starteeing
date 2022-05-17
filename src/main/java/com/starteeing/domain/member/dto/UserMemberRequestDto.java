package com.starteeing.domain.member.dto;

import com.starteeing.domain.member.entity.MemberRole;
import com.starteeing.domain.member.entity.SchoolInfo;
import com.starteeing.domain.member.entity.UserMember;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Builder
@Getter
public class UserMemberRequestDto {

    public static final double STANDARD_TEMPERATURE = 36.5D;

    @Size(max = 4)
    private String name;

    @NotBlank
    private String email;

    private MemberRole memberRole;

    @NotEmpty
    private String nickname;

    private LocalDate birthOfDate;

    private String phoneNumber;

    private String mbti;

    @NotBlank
    private String school;

    @NotEmpty
    private String department;

    @NotNull
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