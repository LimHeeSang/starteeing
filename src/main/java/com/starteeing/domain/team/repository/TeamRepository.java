package com.starteeing.domain.team.repository;

import com.starteeing.domain.team.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {

    @Query("select t from Team t left join fetch t.members where t.id =:id")
    Optional<Team> findByIdWithMembers(@Param("id") Long id);
}
