package com.starting.domain.meeting.repository;

import com.starting.domain.meeting.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, Long> {
}
