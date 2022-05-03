package com.univteeing.domain.member.entity;

import javax.persistence.*;

@Entity
public class Friends {

    @Id
    @GeneratedValue
    @Column(name = "friends_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private UserMember userMember;

    @Column(unique = true)
    private Long friendId;

    @Enumerated(EnumType.STRING)
    private FriendsStatus friendsStatus;
}