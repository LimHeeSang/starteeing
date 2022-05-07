package com.starteeing.domain.dto;


import com.starteeing.domain.freinds.entity.Friend;
import com.starteeing.domain.freinds.entity.FriendStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FriendResponseDto {

    private Long friendId;

    private String nickName;

    private FriendStatus friendStatus;

    public FriendResponseDto(Friend friend) {
        this.friendId = friend.getFriendId();
        this.friendStatus = friend.getFriendsStatus();
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}