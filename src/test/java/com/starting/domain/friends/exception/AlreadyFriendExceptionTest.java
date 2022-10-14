package com.starting.domain.friends.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

class AlreadyFriendExceptionTest {

    @Test
    void create() {
        AlreadyFriendException e = new AlreadyFriendException();

        assertThat(e.getExceptionEnum()).isEqualTo(FriendExEnum.ALREADY_FRIEND);
        assertThat(e.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(e.getMessage()).isEqualTo("이미 친구인 회원입니다.");
    }
}