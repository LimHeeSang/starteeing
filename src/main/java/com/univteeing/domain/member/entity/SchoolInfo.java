package com.univteeing.domain.member.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class SchoolInfo {

    private String school;

    private String department;

    @Column(unique = true)
    private String uniqSchoolNumber;
}