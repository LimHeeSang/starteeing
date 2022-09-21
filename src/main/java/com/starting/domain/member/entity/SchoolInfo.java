package com.starting.domain.member.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Embeddable
public class SchoolInfo {

    private String school;

    private String department;

    @Column(unique = true)
    private String uniqSchoolNumber;
}