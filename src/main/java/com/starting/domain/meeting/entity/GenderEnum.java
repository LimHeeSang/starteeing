package com.starting.domain.meeting.entity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum GenderEnum {
    MALE("M"), FEMALE("F");

    private final String genderType;
}