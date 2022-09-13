package com.starting.domain.friends.repository;

import com.starting.domain.friends.entity.Friend;
import com.starting.domain.friends.entity.FriendStatus;
import com.starting.domain.member.entity.Member;
import com.starting.domain.member.entity.UserMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    Optional<Friend> findByUserMemberAndFriendId(UserMember userMember, Long friendId);

    boolean existsByUserMemberAndFriendId(UserMember userMember, Long friendId);

    List<Friend> findAllByUserMember(UserMember userMember);

    List<Friend> findAllByUserMemberAndFriendStatus(Member member, FriendStatus friendStatus);
}