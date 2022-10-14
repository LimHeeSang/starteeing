package com.starting.domain.friends.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

class AlreadyResponseFriendExceptionTest {

    @Test
    void create() {
        AlreadyResponseFriendException e = new AlreadyResponseFriendException();

        assertThat(e.getExceptionEnum()).isEqualTo(FriendExEnum.ALREADY_RESPONSE_FRIEND);
        assertThat(e.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(e.getMessage()).isEqualTo("이미 친구요청을 받은 회원입니다. 친구수락목록을 확인하세요");
    }
}