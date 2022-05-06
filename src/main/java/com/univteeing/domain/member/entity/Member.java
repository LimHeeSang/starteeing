package com.univteeing.domain.member.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Entity
public abstract class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;

    private String email;

    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}