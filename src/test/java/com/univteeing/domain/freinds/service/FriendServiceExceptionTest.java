package com.univteeing.domain.freinds.service;

import com.univteeing.domain.freinds.repository.FriendRepository;
import com.univteeing.domain.member.entity.UserMember;
import com.univteeing.domain.member.repository.UserMemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@Rollback(value = true)
public class FriendServiceExceptionTest {
    @Autowired
    UserMemberRepository userMemberRepository;
    @Autowired
    FriendRepository friendRepository;
    @Autowired
    FriendService friendService;

    UserMember member1;
    UserMember member2;
    UserMember member3;

    @BeforeEach
    void setUp() {
        member1 = UserMember.builder().nickName("userA").build();
        member2 = UserMember.builder().nickName("userB").build();
        member3 = UserMember.builder().nickName("userC").build();

        userMemberRepository.save(member1);
        userMemberRepository.save(member2);
        userMemberRepository.save(member3);
    }

    @Test
    void 친구요청시_이미보낸적있으면_예외() {
        friendService.requestFriend(member1.getId(), "userB");

        assertThatThrownBy(() -> {
            friendService.requestFriend(member1.getId(), "userB");
        }).isInstanceOf(IllegalCallerException.class);
    }

    @Test
    void 친구요청시_받았던적있으면_예외() {
        friendService.requestFriend(member2.getId(), "userA");

        assertThatThrownBy(() -> {
            friendService.requestFriend(member1.getId(), "userB");
        }).isInstanceOf(IllegalCallerException.class);
    }

    @Test
    void 친구요청시_이미친구인경우_예외() {
        friendService.requestFriend(member1.getId(), "userB");
        friendService.acceptFriend(member2.getId(), "userA");

        assertThatThrownBy(() -> {
            friendService.requestFriend(member1.getId(), "userB");
        }).isInstanceOf(IllegalCallerException.class);
    }
}