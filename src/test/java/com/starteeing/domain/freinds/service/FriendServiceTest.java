package com.starteeing.domain.freinds.service;

import com.starteeing.domain.dto.FriendResponseDto;
import com.starteeing.domain.freinds.entity.Friend;
import com.starteeing.domain.freinds.entity.FriendStatus;
import com.starteeing.domain.freinds.repository.FriendRepository;
import com.starteeing.domain.member.entity.MemberRole;
import com.starteeing.domain.member.entity.SchoolInfo;
import com.starteeing.domain.member.entity.UserMember;
import com.starteeing.domain.member.repository.UserMemberRepository;
import org.apache.tomcat.jni.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
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
        member1 = createUserMember("aaa@naver.com", "userA", "010-1234-0000", "12340000");
        member2 = createUserMember("bbb@naver.com", "userB", "010-1234-0001", "12340001");
        member3 = createUserMember("ccc@naver.com", "userC", "010-1234-0002", "12340002");

        userMemberRepository.save(member1);
        userMemberRepository.save(member2);
        userMemberRepository.save(member3);
    }

    public UserMember createUserMember(String email, String nickName, String phoneNumber, String schoolNumber) {
        SchoolInfo schoolInfo = SchoolInfo.builder()
                .school("순천향대")
                .department("정보보호학과")
                .uniqSchoolNumber(schoolNumber)
                .build();

        return UserMember.builder()
                .name("홍길동")
                .email(email)
                .memberRole(MemberRole.ROLE_USER)
                .nickName(nickName)
                .birthOfDate(LocalDate.of(1998, 9, 4))
                .phoneNumber(phoneNumber)
                .mbti("estj")
                .temperature(37.5D)
                .schoolInfo(schoolInfo)
                .build();
    }

    @Test
    void 친구추가_처음요청() {
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