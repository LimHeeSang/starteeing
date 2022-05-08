package com.starteeing.domain.freinds.repository;

import com.starteeing.domain.freinds.entity.Friend;
import com.starteeing.domain.member.entity.UserMember;
import com.starteeing.domain.member.repository.UserMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class FriendRepositoryTest {

    @Autowired
    private FriendRepository friendRepository;
    @Autowired
    private UserMemberRepository userMemberRepository;

    UserMember userMember1;
    UserMember userMember2;
    UserMember userMember3;
    Friend friend1;
    Friend friend2;

    @BeforeEach
    void setUp() {
        userMember1 = UserMember.builder().build();
        userMember2 = UserMember.builder().build();
        userMember3 = UserMember.builder().build();


        userMemberRepository.save(userMember1);
        userMemberRepository.save(userMember2);
        userMemberRepository.save(userMember3);

        friend1 = Friend.builder().userMember(userMember1)
                .friendId(userMember2.getId())
                .build();

        friend2 = Friend.builder().userMember(userMember1)
                .friendId(userMember3.getId())
                .build();

        friendRepository.save(friend1);
        friendRepository.save(friend2);
    }

    @Test
    void findByUserMemberAndFriendId() {
        Friend findFriend = friendRepository.findByUserMemberAndFriendId(userMember1, userMember2.getId()).get();

        assertThat(findFriend.getUserMember()).isEqualTo(userMember1);
        assertThat(findFriend.getFriendId()).isEqualTo(userMember2.getId());
    }

    @Test
    void existsByUserMemberAndFriendId() {
        boolean result = friendRepository.existsByUserMemberAndFriendId(userMember1, userMember2.getId());
        assertThat(result).isTrue();
    }

    @Test
    void findAllByUserMember() {
        List<Friend> friends = friendRepository.findAllByUserMember(userMember1);
        assertThat(friends).contains(friend1, friend2);
    }
}