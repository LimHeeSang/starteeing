package com.starting.domain.friends.service;

import com.starting.domain.friends.entity.Friend;
import com.starting.domain.friends.entity.FriendStatus;
import com.starting.domain.friends.repository.FriendRepository;
import com.starting.domain.member.entity.UserMember;
import com.starting.domain.member.repository.UserMemberRepository;
import com.starting.test.TestUserMemberFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class FriendServiceExceptionTest {

    @Mock
    UserMemberRepository userMemberRepository;
    @Mock
    FriendRepository friendRepository;
    @InjectMocks
    FriendService friendService;

    UserMember member1;
    UserMember member2;
    UserMember member3;

    Long userMemberId1;
    Long userMemberId2;
    Long userMemberId3;

    @BeforeEach
    void setUp() {
        member1 = TestUserMemberFactory.create();
        member2 = TestUserMemberFactory.create();
        member3 = TestUserMemberFactory.create();

        userMemberId1 = 1L;
        userMemberId2 = 2L;
        userMemberId3 = 3L;

        ReflectionTestUtils.setField(member1, "id", userMemberId1);
        ReflectionTestUtils.setField(member2, "id", userMemberId2);
        ReflectionTestUtils.setField(member3, "id", userMemberId3);
    }

    @Test
    void 친구요청시_이미보낸적있으면_예외() {
        addFriend(member1, member2, FriendStatus.REQUEST);
        addFriend(member2, member1, FriendStatus.RESPONSE);

        setUpCommonGiven();

        assertThatThrownBy(() -> {
            friendService.requestFriend(member1.getId(), "userB");
        }).isInstanceOf(IllegalCallerException.class);
    }

    @Test
    void 친구요청시_받았던적있으면_예외() {
        addFriend(member1, member2, FriendStatus.RESPONSE);
        addFriend(member2, member1, FriendStatus.REQUEST);

        setUpCommonGiven();

        assertThatThrownBy(() -> {
            friendService.requestFriend(member1.getId(), "userB");
        }).isInstanceOf(IllegalCallerException.class);
    }

    @Test
    void 친구요청시_이미친구인경우_예외() {
        addFriend(member1, member2, FriendStatus.ACCEPT);
        addFriend(member2, member1, FriendStatus.ACCEPT);

        setUpCommonGiven();

        assertThatThrownBy(() -> {
            friendService.requestFriend(member1.getId(), "userB");
        }).isInstanceOf(IllegalCallerException.class);
    }

    private void setUpCommonGiven() {
        given(userMemberRepository.findById(userMemberId1)).willReturn(
                Optional.ofNullable(member1));
        given(userMemberRepository.findByNickName("userB")).willReturn(
                Optional.ofNullable(member2));

        given(friendRepository.existsByUserMemberAndFriendId(member1, member2.getId())).willReturn(true);
        given(friendRepository.findByUserMemberAndFriendId(member1, member2.getId()))
                .willReturn(Optional.ofNullable(member1.getFriends().get(0)));
        given(friendRepository.findByUserMemberAndFriendId(member2, member1.getId()))
                .willReturn(Optional.ofNullable(member2.getFriends().get(0)));
    }

    private void addFriend(UserMember toMember, UserMember fromMember, FriendStatus friendStatus) {
        toMember.getFriends().add(Friend.builder()
                .userMember(fromMember)
                .friendId(fromMember.getId())
                .friendStatus(friendStatus)
                .build());
    }
}