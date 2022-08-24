package com.starting.domain.meeting.controller;

import com.starting.domain.meeting.dto.MatchListResponseDto;
import com.starting.domain.meeting.dto.TicketResponseDto;
import com.starting.domain.meeting.service.BoxService;
import com.starting.domain.meeting.service.MatchService;
import com.starting.golbal.response.ResponseService;
import com.starting.golbal.response.result.CommonResult;
import com.starting.golbal.response.result.SingleResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MeetingController {

    private final BoxService boxService;
    private final MatchService matchService;
    private final ResponseService responseService;

    /**
     * 티켓 넣기
     */
    @PostMapping("/ticket/put/{memberId}/{teamId}")
    public ResponseEntity<CommonResult> putTicket(@PathVariable Long memberId, @PathVariable Long teamId) {
        boxService.putTicket(memberId, teamId);
        return ResponseEntity.ok(responseService.getSuccessResult());
    }

    /**
     * 티켓 랜덤 뽑기
     */
    @PostMapping("/ticket/draw/{teamId}")
    public ResponseEntity<SingleResult> drawRandomTicket(@PathVariable Long teamId) {
        TicketResponseDto ticketResponseDto = boxService.drawRandomTicket(teamId);
        return ResponseEntity.ok(responseService.getSingleResult(ticketResponseDto));
    }

    /**
     * 매칭 정보 조회(페이징)
     */
    @GetMapping("/ticket")
    public ResponseEntity<SingleResult> getTickets(Pageable pageable) {
        Page<MatchListResponseDto> matches = matchService.getMatches(pageable);
        return ResponseEntity.ok(responseService.getSingleResult(matches));
    }
}
