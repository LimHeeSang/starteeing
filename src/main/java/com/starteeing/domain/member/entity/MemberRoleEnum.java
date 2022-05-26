package com.starteeing.domain.member.entity;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;

public enum MemberRoleEnum {
    ROLE_USER, ROLE_ADMIN;
}