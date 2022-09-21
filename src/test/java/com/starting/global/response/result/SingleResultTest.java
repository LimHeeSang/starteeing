package com.starting.global.response.result;

import com.starting.domain.member.entity.UserMember;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class SingleResultTest {

    @Test
    void create() {
        UserMember member = UserMember.builder().build();
        SingleResult<UserMember> result = SingleResult.createSingleResult(member);

        assertThat(result.getData()).isEqualTo(member);
    }
}