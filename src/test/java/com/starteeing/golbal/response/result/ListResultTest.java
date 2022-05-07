package com.starteeing.golbal.response.result;

import com.starteeing.domain.member.entity.UserMember;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ListResultTest {

    @Test
    void create() {
        UserMember member1 = UserMember.builder().build();
        UserMember member2 = UserMember.builder().build();
        UserMember member3 = UserMember.builder().build();

        List<UserMember> members = Arrays.asList(member1, member2, member3);

        ListResult<UserMember> result = new ListResult<>(members);

        assertThat(result.getData()).contains(member1, member2, member3);
    }
}