package com.starteeing.domain.freinds.service;

import com.starteeing.domain.freinds.repository.FriendRepository;
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

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
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
        member1 = createUserMember("aaa@naver.com", "userA", "010-1234-0000", "12340000");
        member2 = createUserMember("bbb@naver.com", "userB", "010-1234-0001", "12340001");
        member3 = createUserMember("ccc@naver.com", "userC", "010-1234-0002", "12340002");

        System.out.println("----------");
        System.out.println(member1);
        System.out.println(member2);
        System.out.println(member3);

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