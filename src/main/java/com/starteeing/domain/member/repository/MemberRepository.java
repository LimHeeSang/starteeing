package com.starteeing.domain.member.repository;

import com.starteeing.domain.member.entity.Member;
import com.starteeing.domain.member.entity.UserMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByEmail(String email);

    @Query("select m from Member m left join m.memberRoles")
    Optional<Member> findByEmailWithMemberRoles(String email);
}