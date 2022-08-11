package com.starting.domain.meeting.repository;

import com.starting.domain.meeting.entity.Box;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoxRepository extends JpaRepository<Box, Long> {
}
