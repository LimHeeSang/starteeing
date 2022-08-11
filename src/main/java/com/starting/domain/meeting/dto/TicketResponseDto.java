package com.starting.domain.meeting.dto;

import com.starting.domain.meeting.entity.Ticket;
import lombok.Builder;

public class TicketResponseDto {

    private String teamName;

    private String gender;

    private int memberCount;

    @Builder
    public TicketResponseDto(Ticket ticket) {
        teamName = ticket.getTeam().getName();
        gender = ticket.getGenderEnum().name();
        memberCount = ticket.getMemberCount();
    }
}
