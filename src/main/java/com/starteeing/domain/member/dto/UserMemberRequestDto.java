package com.starteeing.domain.member.dto;

import com.starteeing.domain.member.entity.SchoolInfo;
import com.starteeing.domain.member.entity.UserMember;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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

    @NotBlank
    private String password;

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

    public UserMember toEntity(BCryptPasswordEncoder bCryptPasswordEncoder) {
        return UserMember.builder()
                .name(name)
                .email(email)
                .password(bCryptPasswordEncoder.encode(password))
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