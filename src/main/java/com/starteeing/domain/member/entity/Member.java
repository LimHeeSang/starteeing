package com.starteeing.domain.member.entity;

import com.starteeing.domain.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Getter
@Entity
public abstract class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    protected List<MemberRole> memberRoles = new ArrayList<>();

    public List<MemberRoleEnum> mapToMemberRoleEnum() {
        return memberRoles.stream()
                .map(MemberRole::getMemberRoleEnum)
                .collect(Collectors.toList());
    }
}