package com.univteeing.domain.freinds.repository;

import com.univteeing.domain.freinds.entity.Friend;
import com.univteeing.domain.member.entity.UserMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    Optional<Friend> findByUserMemberAndFriendId(UserMember userMember, Long friendId);

    boolean existsByUserMemberAndFriendId(UserMember userMember, Long friendId);

    List<Friend> findAllByUserMember(UserMember userMember);
}