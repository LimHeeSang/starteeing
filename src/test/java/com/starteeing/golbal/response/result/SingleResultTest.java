package com.starteeing.golbal.response.result;

import com.starteeing.domain.member.entity.UserMember;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SingleResultTest {

    @Test
    void create() {
        UserMember member = UserMember.builder().build();
        SingleResult<UserMember> result = new SingleResult<>(member);

        Assertions.assertThat(result.getData()).isEqualTo(member);
    }
}