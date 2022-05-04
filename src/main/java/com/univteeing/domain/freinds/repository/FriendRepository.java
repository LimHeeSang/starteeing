package com.univteeing.domain.freinds.repository;

import com.univteeing.domain.freinds.entity.Friend;
import com.univteeing.domain.member.entity.UserMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    List<Friend> findFriendsByUserMember(UserMember userMember);

}