package com.starting.domain.team.repository;

import com.starting.domain.member.entity.UserMember;
import com.starting.domain.team.entity.Team;
import com.starting.domain.team.entity.TeamUserMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TeamUserMemberRepository extends JpaRepository<TeamUserMember, Long> {

    Optional<TeamUserMember> findByTeamAndUserMember(Team team, UserMember userMember);

    @Query("select tm from TeamUserMember tm left join fetch tm.team where tm.userMember =: useMember")
    List<TeamUserMember> findAllByUserMemberWithTeam(@Param("userMember") UserMember userMember);

    boolean existsByTeamAndUserMember(Team team, UserMember userMember);

}