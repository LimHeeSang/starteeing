package com.starteeing.domain.member.entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"id", "member"})
@Builder
@Entity
public class MemberRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MemberRole_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private MemberRoleEnum memberRoleEnum;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    public MemberRole(MemberRoleEnum memberRoleEnum) {
        this.memberRoleEnum = memberRoleEnum;
    }

    public MemberRoleEnum getMemberRoleEnum() {
        return memberRoleEnum;
    }
}