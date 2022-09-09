package com.starting.domain.meeting.entity;

import com.starting.domain.common.BaseTimeEntity;
import com.starting.domain.meeting.exception.NotExistTicketException;
import com.starting.domain.team.entity.Team;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Box extends BaseTimeEntity {

    public static final int RANDOM_TICKET_INDEX = 0;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "box_id")
    private Long id;

    @OneToMany(mappedBy = "box", cascade = CascadeType.ALL)
    private final List<Ticket> tickets = new LinkedList<>();

    public void putTicket(Ticket ticket) {
        tickets.add(ticket);
    }

    public Ticket drawRandomTicket(Team team) {
        List<Ticket> filterTickets = filterGenderTickets(team);

        if (tickets.isEmpty() && filterTickets.isEmpty()) {
            throw new NotExistTicketException();
        }

        return filterTickets.remove(RANDOM_TICKET_INDEX);
    }

    private List<Ticket> filterGenderTickets(Team team) {
        return tickets.stream()
                .filter(ticket -> ticket.getGenderEnum().equals(team.getTeamGender()))
                .collect(Collectors.toList());
    }
}
