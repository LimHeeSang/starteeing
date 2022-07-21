package com.starting.domain.member.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Embeddable
public class SchoolInfo {

    @Column(nullable = false)
    private String school;

    @Column(nullable = false)
    private String department;

    @Column(unique = true, nullable = false)
    private String uniqSchoolNumber;
}