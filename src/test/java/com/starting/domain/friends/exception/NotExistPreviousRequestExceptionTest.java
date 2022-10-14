package com.starting.domain.friends.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

class NotExistPreviousRequestExceptionTest {

    @Test
    void create() {
        NotExistPreviousRequestException e = new NotExistPreviousRequestException();

        assertThat(e.getExceptionEnum()).isEqualTo(FriendExEnum.NOT_EXIST_PREVIOUS_REQUEST);
        assertThat(e.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(e.getMessage()).isEqualTo("이전 친구 요청이 없습니다.");
    }
}