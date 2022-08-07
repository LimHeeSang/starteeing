package com.starting.domain.meeting.repository;

import com.starting.domain.meeting.entity.Ticket;
import com.starting.domain.team.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    boolean existsByTeam(Team team);
}
