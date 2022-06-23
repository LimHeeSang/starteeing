package com.starteeing.domain.member.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

class NotExistTokenExceptionTest {

    @Test
    void create() {
        NotExistTokenException e = new NotExistTokenException();

        assertThat(e.getExceptionEnum()).isEqualTo(MemberExEnum.NOT_EXIST_TOKEN);
        assertThat(e.getHttpStatus()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(e.getMessage()).isEqualTo("토큰이 존재하지 않습니다.");
    }
}