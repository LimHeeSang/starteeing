package com.univteeing.domain.member.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
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

    @Embedded
    private SchoolInfo schoolInfo;
}