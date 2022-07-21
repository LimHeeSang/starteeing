package com.starting.domain.member.repository;

import com.starting.domain.member.entity.UserMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserMemberRepository extends JpaRepository<UserMember, Long> {

    Optional<UserMember> findByNickName(String nickname);

    @Query("select m.nickName from UserMember m where m.id in :ids")
    List<String> findNicknamesByIdList(List<Long> ids);
}