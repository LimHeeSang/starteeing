package com.starting.domain.meeting.entity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum GenderEnum {
    MALE("M"), FEMALE("F");

    private final String genderType;

    public boolean isMale() {
        return genderType.equals(MALE.genderType);
    }

    public boolean isFemale() {
        return genderType.equals(FEMALE.genderType);
    }
}