package com.starting.domain.friends.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

class FriendExEnumTest {

    @Test
    void not_exist_friend() {
        FriendExEnum friendExEnum = FriendExEnum.NOT_EXIST_FRIEND;

        assertThat(friendExEnum.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(friendExEnum.getCode()).isEqualTo("F0001");
        assertThat(friendExEnum.getMessage()).isEqualTo("찾으려는 친구가 없습니다.");
    }

    @Test
    void not_exist_previous_request() {
        FriendExEnum friendExEnum = FriendExEnum.NOT_EXIST_PREVIOUS_REQUEST;

        assertThat(friendExEnum.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(friendExEnum.getCode()).isEqualTo("F0002");
        assertThat(friendExEnum.getMessage()).isEqualTo("이전 요청이 없습니다.");
    }

    @Test
    void already_request_friend() {
        FriendExEnum friendExEnum = FriendExEnum.ALREADY_REQUEST_FRIEND;

        assertThat(friendExEnum.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(friendExEnum.getCode()).isEqualTo("F0003");
        assertThat(friendExEnum.getMessage()).isEqualTo("이미 친구요청을 보낸 회원입니다.");
    }

    @Test
    void already_friend() {
        FriendExEnum friendExEnum = FriendExEnum.ALREADY_FRIEND;

        assertThat(friendExEnum.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(friendExEnum.getCode()).isEqualTo("F0004");
        assertThat(friendExEnum.getMessage()).isEqualTo("이미 친구인 회원입니다.");
    }

    @Test
    void already_response_friend() {
        FriendExEnum friendExEnum = FriendExEnum.ALREADY_RESPONSE_FRIEND;

        assertThat(friendExEnum.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(friendExEnum.getCode()).isEqualTo("F0005");
        assertThat(friendExEnum.getMessage()).isEqualTo("이미 친구요청을 받은 회원입니다. 친구수락목록을 확인하세요");
    }
}