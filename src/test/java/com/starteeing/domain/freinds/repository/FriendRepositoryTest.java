package com.starteeing.domain.freinds.repository;

import com.starteeing.domain.freinds.entity.Friend;
import com.starteeing.domain.freinds.entity.FriendStatus;
import com.starteeing.domain.member.entity.MemberRole;
import com.starteeing.domain.member.entity.SchoolInfo;
import com.starteeing.domain.member.entity.UserMember;
import com.starteeing.domain.member.repository.UserMemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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
        userMember1 = createUserMember("aaa@naver.com", "userA", "010-1234-0000", "12340000");
        userMember2 = createUserMember("bbb@naver.com", "userB", "010-1234-0001", "12340001");
        userMember3 = createUserMember("ccc@naver.com", "userC", "010-1234-0002", "12340002");

        userMemberRepository.save(userMember1);
        userMemberRepository.save(userMember2);
        userMemberRepository.save(userMember3);

        friend1 = Friend.builder().userMember(userMember1)
                .friendId(userMember2.getId())
                .friendsStatus(FriendStatus.REQUEST)
                .build();
        friend2 = Friend.builder().userMember(userMember1)
                .friendId(userMember3.getId())
                .friendsStatus(FriendStatus.REQUEST)
                .build();

        friendRepository.save(friend1);
        friendRepository.save(friend2);
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