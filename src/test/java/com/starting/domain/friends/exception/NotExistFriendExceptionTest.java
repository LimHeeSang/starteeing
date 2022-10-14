package com.starting.domain.friends.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

class NotExistFriendExceptionTest {

    @Test
    void create() {
        NotExistFriendException e = new NotExistFriendException();

        assertThat(e.getExceptionEnum()).isEqualTo(FriendExEnum.NOT_EXIST_FRIEND);
        assertThat(e.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(e.getMessage()).isEqualTo("찾으려는 친구가 없습니다.");
    }
}