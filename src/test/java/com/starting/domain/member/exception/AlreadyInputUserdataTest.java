package com.starting.domain.member.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

class AlreadyInputUserdataTest {

    @Test
    void create() {
        AlreadyInputUserdata e = new AlreadyInputUserdata();

        assertThat(e.getExceptionEnum()).isEqualTo(MemberExEnum.ALREADY_INPUT_USERDATA);
        assertThat(e.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(e.getMessage()).isEqualTo("이미 유저 정보를 입력했습니다.");
    }

}