package com.univteeing.domain.member.entity;

import javax.persistence.*;
import java.time.LocalDate;

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