package com.starting.domain.friends.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

class AlreadyRequestFriendExceptionTest {

    @Test
    void create() {
        AlreadyRequestFriendException e = new AlreadyRequestFriendException();

        assertThat(e.getExceptionEnum()).isEqualTo(FriendExEnum.ALREADY_REQUEST_FRIEND);
        assertThat(e.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(e.getMessage()).isEqualTo("이미 친구요청을 보낸 회원입니다.");
    }
}