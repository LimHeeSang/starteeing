package com.starting.domain.member.repository;

import com.starting.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByEmail(String email);

    Optional<Member> findByEmail(String email);

    @Query("select m from Member m left join fetch m.memberRoles where m.email =:email")
    Optional<Member> findByEmailWithMemberRoles(@Param("email") String email);

    @Query("select m from Member m left join fetch m.refreshToken where m.email =:email")
    Optional<Member> findByEmailWithRefreshToken(@Param("email") String email);

    Optional<Member> findByUserId(String userId);

    @Query("select m from Member m where m.id in :ids")
    List<Member> findAllById(List<Long> ids);
}