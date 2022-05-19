package com.starteeing.domain.member.repository;

import com.starteeing.domain.member.entity.Member;
import com.starteeing.domain.member.entity.UserMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByEmail(String email);

    Optional<Member> findByEmail(String email);
}