package com.starting.domain.friends.dto;


import com.starting.domain.friends.entity.Friend;
import com.starting.domain.friends.entity.FriendStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class FriendListResponseDto {

    private List<FriendResponseDto> result;

    @Builder
    public FriendListResponseDto(List<Friend> friends, List<String> nicknames) {
        List<FriendResponseDto> friendListResponseDto = friends.stream()
                .map(friend -> new FriendResponseDto(friend.getFriendId(), friend.getFriendStatus()))
                .collect(Collectors.toList());

        addNicknames(friendListResponseDto, nicknames);
        result = friendListResponseDto;
    }

    private void addNicknames(List<FriendResponseDto> friendListResponseDto, List<String> nicknames) {
        int idx = 0;
        for (FriendResponseDto friendResponseDto : friendListResponseDto) {
            friendResponseDto.addNickName(nicknames.get(idx++));
        }
    }

    @Getter
    public static class FriendResponseDto {
        private Long friendId;

        private String nickname;

        private FriendStatus friendStatus;

        public FriendResponseDto(Long friendId, FriendStatus friendStatus) {
            this.friendId = friendId;
            this.friendStatus = friendStatus;
        }

        public void addNickName(String nickName) {
            this.nickname = nickName;
        }
    }
}