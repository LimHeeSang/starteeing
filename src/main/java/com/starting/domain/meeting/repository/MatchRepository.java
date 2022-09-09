package com.starting.domain.meeting.repository;

import com.starting.domain.meeting.entity.Matches;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Matches, Long> {
}
