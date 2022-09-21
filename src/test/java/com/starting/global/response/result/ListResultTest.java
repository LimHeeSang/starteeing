package com.starting.global.response.result;

import com.starting.domain.member.entity.UserMember;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ListResultTest {

    @Test
    void create() {
        UserMember member1 = UserMember.builder().build();
        UserMember member2 = UserMember.builder().build();
        UserMember member3 = UserMember.builder().build();

        List<UserMember> members = Arrays.asList(member1, member2, member3);
        ListResult<UserMember> result = ListResult.createListResult(members);

        assertThat(result.getData()).contains(member1, member2, member3);
    }
}