package com.starting.domain.friends.entity;

import com.starting.domain.common.BaseTimeEntity;
import com.starting.domain.friends.exception.AlreadyFriendException;
import com.starting.domain.friends.exception.AlreadyRequestFriendException;
import com.starting.domain.friends.exception.AlreadyResponseFriendException;
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
            throw new AlreadyRequestFriendException();
        }
        if (isStatusAccept()) {
            throw new AlreadyFriendException();
        }
        if (isStatusResponse()) {
            throw new AlreadyResponseFriendException();
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