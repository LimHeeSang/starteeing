package com.starting.domain.meeting.service;

import com.starting.domain.meeting.dto.TicketResponseDto;
import com.starting.domain.meeting.entity.Box;
import com.starting.domain.meeting.entity.BoxContainer;
import com.starting.domain.meeting.entity.Matches;
import com.starting.domain.meeting.entity.Ticket;
import com.starting.domain.meeting.exception.NotEqualGenderException;
import com.starting.domain.meeting.repository.MatchRepository;
import com.starting.domain.team.entity.Team;
import com.starting.domain.team.exception.NotExistTeamException;
import com.starting.domain.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class BoxService {

    private final TicketService ticketService;
    private final BoxContainer boxContainer;
    private final TeamRepository teamRepository;
    private final MatchRepository matchRepository;

    /**
     * 티켓 넣기
     */
    public void putTicket(Long memberId, Long teamId) {
        Ticket createdTicket = ticketService.createTicket(memberId, teamId);
        Box box = boxContainer.getBoxNum(createdTicket.getMemberCount());
        box.putTicket(createdTicket);
    }

    /**
     * 티켓 랜덤 뽑기
     */
    public TicketResponseDto drawRandomTicket(Long teamId) {
        Team findTeam = teamRepository.findById(teamId).orElseThrow(NotExistTeamException::new);

        if (findTeam.isNotEqualMembersGender()) {
            throw new NotEqualGenderException();
        }

        Box box = boxContainer.getBoxNum(findTeam.getUserMemberCount());
        Ticket drawTicket = box.drawRandomTicket(findTeam);

        createMatch(findTeam, drawTicket.getTeam());
        return TicketResponseDto.builder().ticket(drawTicket).build();
    }

    private void createMatch(Team findTeam, Team opposeTeam) {
        Matches createMatches = Matches.builder().teams(List.of(findTeam, opposeTeam)).build();
        matchRepository.save(createMatches);
    }
}
