package com.starteeing.domain.team.repository;

import com.starteeing.domain.member.entity.UserMember;
import com.starteeing.domain.team.entity.Team;
import com.starteeing.domain.team.entity.TeamUserMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TeamUserMemberRepository extends JpaRepository<TeamUserMember, Long> {

    Optional<TeamUserMember> findByTeamAndUserMember(Team team, UserMember userMember);

    @Query("select tm from TeamUserMember tm left join fetch tm.team where tm.userMember =: useMember")
    List<TeamUserMember> findAllByUserMemberWithTeam(@Param("userMember") UserMember userMember);

    //test 해보고 되면 이 메소드로 리팩토링하기
    Optional<TeamUserMember> findByTeamAAndUserMember(Long teamId, Long memberId);
}
