package com.univteeing.domain.freinds.entity;

import com.univteeing.domain.member.entity.Member;
import com.univteeing.domain.member.entity.UserMember;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Friend {

    @Id
    @GeneratedValue
    @Column(name = "friend_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member userMember;

    @Column(name = "member_friend_id")
    private Long friendId;

    @Enumerated(EnumType.STRING)
    private FriendStatus friendsStatus;

    public Long getFriendId() {
        return friendId;
    }

    public FriendStatus getFriendsStatus() {
        return friendsStatus;
    }

    public boolean isStatusResponse() {
        return friendsStatus == FriendStatus.RESPONSE;
    }

    public void changeStatusToAccept() {
        friendsStatus = FriendStatus.ACCEPT;
    }
}