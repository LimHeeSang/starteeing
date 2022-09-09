package com.starting.domain.friends.entity;

import com.starting.domain.common.BaseTimeEntity;
import com.starting.domain.member.entity.UserMember;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Friend extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friend_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private UserMember userMember;

    @Column(name = "member_friend_id", nullable = false)
    private Long friendId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FriendStatus friendStatus;

    @Builder
    public Friend(UserMember userMember, Long friendId, FriendStatus friendStatus) {
        this.userMember = userMember;
        this.friendId = friendId;
        this.friendStatus = friendStatus;
    }

    public boolean isStatusResponse() {
        return friendStatus == FriendStatus.RESPONSE;
    }

    private boolean isStatusAccept() {
        return friendStatus == FriendStatus.ACCEPT;
    }

    private boolean isStatusRequest() {
        return friendStatus == FriendStatus.REQUEST;
    }

    private void changeStatusToAccept() {
        friendStatus = FriendStatus.ACCEPT;
    }

    private void changeStatusToReject() {
        friendStatus = FriendStatus.REJECT;
    }

    private void changeStatusToRequest() {
        friendStatus = FriendStatus.REQUEST;
    }

    private void changeStatusToResponse() {
        friendStatus = FriendStatus.RESPONSE;
    }

    private void changeStatusToDelete() {
        friendStatus = FriendStatus.DELETE;
    }

    public void reRequestFriend() {
        if (isStatusRequest()) {
            throw new IllegalCallerException("이미 친구요청을 보낸 회원입니다.");
        }
        if (isStatusAccept()) {
            throw new IllegalCallerException("이미 친구인 회원입니다.");
        }
        if (isStatusResponse()) {
            throw new IllegalCallerException("이미 친구요청을 받은 회원입니다. 친구수락목록을 확인하세요");
        }
        changeStatusToRequest();
    }

    public void reResponseFriend() {
        changeStatusToResponse();
    }

    public void acceptFriend() {
        changeStatusToAccept();
    }

    public void rejectFriend() {
        changeStatusToReject();
    }

    public void deleteFriend() {
        changeStatusToDelete();
    }
}