package com.starteeing.domain.friends.service;

import com.starteeing.domain.friends.entity.Friend;
import com.starteeing.domain.friends.entity.FriendStatus;
import com.starteeing.domain.friends.repository.FriendRepository;
import com.starteeing.domain.member.entity.MemberRole;
import com.starteeing.domain.member.entity.SchoolInfo;
import com.starteeing.domain.member.entity.UserMember;
import com.starteeing.domain.member.repository.UserMemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
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
        member1 = createUserMember("aaa@naver.com", "userA", "010-1234-0000", "12340000");
        member2 = createUserMember("bbb@naver.com", "userB", "010-1234-0001", "12340001");
        member3 = createUserMember("ccc@naver.com", "userC", "010-1234-0002", "12340002");

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

    private UserMember createUserMember(String email, String nickName, String phoneNumber, String schoolNumber) {
        return UserMember.builder()
                .name("홍길동")
                .email(email)
                .memberRole(MemberRole.ROLE_USER)
                .nickName(nickName)
                .birthOfDate(LocalDate.of(1998, 9, 4))
                .phoneNumber(phoneNumber)
                .mbti("estj")
                .temperature(37.5D)
                .schoolInfo(createSchoolInfo(schoolNumber))
                .build();
    }

    private SchoolInfo createSchoolInfo(String schoolNumber) {
        return SchoolInfo.builder()
                .school("순천향대")
                .department("정보보호학과")
                .uniqSchoolNumber(schoolNumber)
                .build();
    }

    private void addFriend(UserMember toMember, UserMember fromMember, FriendStatus friendStatus) {
        toMember.getFriends().add(Friend.builder()
                .userMember(fromMember)
                .friendId(fromMember.getId())
                .friendsStatus(friendStatus)
                .build());
    }
}