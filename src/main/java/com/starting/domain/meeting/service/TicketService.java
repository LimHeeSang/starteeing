package com.starting.domain.meeting.service;

import com.starting.domain.meeting.entity.Ticket;
import com.starting.domain.meeting.exception.ExistTicketException;
import com.starting.domain.meeting.exception.NotEqualGenderException;
import com.starting.domain.meeting.repository.TicketRepository;
import com.starting.domain.member.entity.UserMember;
import com.starting.domain.member.exception.NotExistMemberException;
import com.starting.domain.member.repository.UserMemberRepository;
import com.starting.domain.team.entity.Team;
import com.starting.domain.team.exception.NotExistTeamException;
import com.starting.domain.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class TicketService {

    private final UserMemberRepository userMemberRepository;
    private final TeamRepository teamRepository;
    private final TicketRepository ticketRepository;

    /**
     * 티켓 생성
     */
    public Ticket createTicket(Long memberId, Long teamId) {
        UserMember findUserMember = userMemberRepository.findById(memberId).orElseThrow(NotExistMemberException::new);
        Team findTeam = teamRepository.findById(teamId).orElseThrow(NotExistTeamException::new);

        if (ticketRepository.existsByTeam(findTeam)) {
            throw new ExistTicketException();
        }
        if (findTeam.isNotEqualMembersGender()) {
            throw new NotEqualGenderException();
        }

        Ticket createTicket = Ticket.builder()
                .userMember(findUserMember)
                .team(findTeam)
                .build();
        return ticketRepository.save(createTicket);
    }
}
