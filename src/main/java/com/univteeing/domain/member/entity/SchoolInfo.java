package com.univteeing.domain.member.entity;

import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SchoolInfo {

    private String school;

    private String department;

    @Column(unique = true)
    private String uniqSchoolNumber;
}