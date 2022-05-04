package com.univteeing.domain.freinds.repository;

import com.univteeing.domain.freinds.entity.Friend;
import com.univteeing.domain.freinds.entity.FriendStatus;
import com.univteeing.domain.member.entity.UserMember;
import com.univteeing.domain.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class FriendRepositoryTest {

    @Autowired
    EntityManager em;
    @Autowired
    FriendRepository friendRepository;
    @Autowired
    MemberRepository memberRepository;

    UserMember member1;
    UserMember member2;
    UserMember member3;

    @BeforeEach
    void setUp() {
        member1 = UserMember.builder().name("userA").build();
        member2 = UserMember.builder().name("userB").build();
        member3 = UserMember.builder().name("userC").build();
    }

    @Test
    void 친구추가_요청() {
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        member1.requestFriend(member2);
        member1.requestFriend(member3);

        List<Friend> friends1 = friendRepository.findFriendsByUserMember(member1);
        List<Friend> friends2 = friendRepository.findFriendsByUserMember(member2);

        assertThat(friends1.size()).isEqualTo(2);
        assertThat(friends1.get(0).getFriendId()).isEqualTo(member2.getId());
        assertThat(friends1.get(0).getFriendsStatus()).isEqualTo(FriendStatus.REQUEST);

        assertThat(friends1.get(1).getFriendId()).isEqualTo(member3.getId());
        assertThat(friends1.get(1).getFriendsStatus()).isEqualTo(FriendStatus.REQUEST);

        assertThat(friends2.get(0).getFriendId()).isEqualTo(member1.getId());
        assertThat(friends2.get(0).getFriendsStatus()).isEqualTo(FriendStatus.RESPONSE);
    }
}