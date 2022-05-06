package com.univteeing.domain.freinds.service;

import com.univteeing.domain.dto.FriendResponseDto;
import com.univteeing.domain.freinds.entity.Friend;
import com.univteeing.domain.freinds.entity.FriendStatus;
import com.univteeing.domain.freinds.repository.FriendRepository;
import com.univteeing.domain.member.entity.UserMember;
import com.univteeing.domain.member.repository.UserMemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = true)
class FriendServiceTest {
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
    void 친구추가_처음요청() {
        //member1 -> member2에게 처음 친추요청

        friendService.requestFriend(member1.getId(), "userB");

        Friend friend1 = friendRepository.findByUserMemberAndFriendId(member1, member2.getId()).get();
        Friend friend2 = friendRepository.findByUserMemberAndFriendId(member2, member1.getId()).get();

        assertThat(friend1.getFriendId()).isEqualTo(member2.getId());
        assertThat(friend1.getFriendsStatus()).isEqualTo(FriendStatus.REQUEST);

        assertThat(friend2.getFriendId()).isEqualTo(member1.getId());
        assertThat(friend2.getFriendsStatus()).isEqualTo(FriendStatus.RESPONSE);
    }

    @Test
    void 친구추가_재요청() {
        //member1 -> member2에게 처음 친추요청
        friendService.requestFriend(member1.getId(), "userB");
        friendService.acceptFriend(member2.getId(), "userA");
        friendService.deleteFriend(member2.getId(), "userA");

        friendService.requestFriend(member1.getId(), "userB");

        Friend friend1 = friendRepository.findByUserMemberAndFriendId(member1, member2.getId()).get();
        Friend friend2 = friendRepository.findByUserMemberAndFriendId(member2, member1.getId()).get();

        assertThat(friend1.getFriendId()).isEqualTo(member2.getId());
        assertThat(friend1.getFriendsStatus()).isEqualTo(FriendStatus.REQUEST);

        assertThat(friend2.getFriendId()).isEqualTo(member1.getId());
        assertThat(friend2.getFriendsStatus()).isEqualTo(FriendStatus.RESPONSE);
    }

    @Test
    void 친구요청_수락() {
        friendService.requestFriend(member1.getId(), "userB");
        friendService.acceptFriend(member2.getId(), "userA");

        Friend friend1 = friendRepository.findByUserMemberAndFriendId(member1, member2.getId()).get();
        Friend friend2 = friendRepository.findByUserMemberAndFriendId(member2, member1.getId()).get();

        assertThat(friend1.getFriendId()).isEqualTo(member2.getId());
        assertThat(friend1.getFriendsStatus()).isEqualTo(FriendStatus.ACCEPT);

        assertThat(friend2.getFriendId()).isEqualTo(member1.getId());
        assertThat(friend2.getFriendsStatus()).isEqualTo(FriendStatus.ACCEPT);
    }

    @Test
    void 친구요청_거절() {
        friendService.requestFriend(member1.getId(), "userB");
        friendService.rejectFriend(member2.getId(), "userA");

        Friend friend1 = friendRepository.findByUserMemberAndFriendId(member1, member2.getId()).get();
        Friend friend2 = friendRepository.findByUserMemberAndFriendId(member2, member1.getId()).get();

        assertThat(friend1.getFriendId()).isEqualTo(member2.getId());
        assertThat(friend1.getFriendsStatus()).isEqualTo(FriendStatus.REJECT);

        assertThat(friend2.getFriendId()).isEqualTo(member1.getId());
        assertThat(friend2.getFriendsStatus()).isEqualTo(FriendStatus.REJECT);
    }

    @Test
    void 친구요청_삭제() {
        friendService.requestFriend(member1.getId(), "userB");
        friendService.acceptFriend(member2.getId(), "userA");

        friendService.deleteFriend(member1.getId(), "userB");

        Friend friend1 = friendRepository.findByUserMemberAndFriendId(member1, member2.getId()).get();
        Friend friend2 = friendRepository.findByUserMemberAndFriendId(member2, member1.getId()).get();

        assertThat(friend1.getFriendId()).isEqualTo(member2.getId());
        assertThat(friend1.getFriendsStatus()).isEqualTo(FriendStatus.DELETE);

        assertThat(friend2.getFriendId()).isEqualTo(member1.getId());
        assertThat(friend2.getFriendsStatus()).isEqualTo(FriendStatus.DELETE);
    }

    @Test
    void 친구리스트_조회() {
        friendService.requestFriend(member1.getId(), "userB");
        friendService.requestFriend(member3.getId(), "userB");

        friendService.acceptFriend(member2.getId(), "userA");
        friendService.acceptFriend(member2.getId(), "userC");

        List<FriendResponseDto> friendsList = friendService.getFriendsList(member2.getId());

        List<Long> ids = Arrays.asList(member1.getId(), member3.getId());
        List<String> nicknames = Arrays.asList("userA", "userC");
        int idx = 0;
        for (FriendResponseDto friendResponseDto : friendsList) {
            assertThat(friendResponseDto.getFriendId()).isEqualTo(ids.get(idx));
            assertThat(friendResponseDto.getFriendStatus()).isEqualTo(FriendStatus.ACCEPT);
            assertThat(friendResponseDto.getNickName()).isEqualTo(nicknames.get(idx++));
        }
    }
}