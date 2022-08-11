package com.starting.domain.meeting.entity;

import com.starting.domain.common.BaseTimeEntity;
import com.starting.domain.meeting.exception.NotExistTicketException;
import com.starting.domain.team.entity.Team;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@SuperBuilder
@NoArgsConstructor
@Entity
public class Box extends BaseTimeEntity {

    public static final int RANDOM_TICKET_INDEX = 0;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "box_id")
    private Long id;

    @OneToMany(mappedBy = "box", cascade = CascadeType.ALL)
    private List<Ticket> tickets = new LinkedList<>();

    public void putTicket(Ticket ticket) {
        tickets.add(ticket);
    }

    public void pullTicket(Ticket ticket) {
        tickets.remove(ticket);
    }

    public Ticket drawRandomTicket(Team team) {
        // TODO: 2022-08-12 셔플 없애고 티켓 진짜 삭제로 로직 바꾸고 상대팀도 매칭기록 기록하기
        //성별에 관한 티켓 필터링 리팩토링
        List<Ticket> filterTicket = tickets.stream()
                .filter(ticket -> team.getMatchedTeamId().contains(ticket.getId())
                && ticket.getGenderEnum().equals(team.getTeamGender()))
                .collect(Collectors.toList());

        if (tickets.isEmpty() && filterTicket.isEmpty()) {
            throw new NotExistTicketException();
        }

        Ticket drawTicket = filterTicket.get(RANDOM_TICKET_INDEX);
        team.addMatchedTeamId(drawTicket.getId());
        //상대팀도 매칭기록

        return drawTicket;
    }

    public boolean isExistTicket(Ticket ticket) {
        return tickets.contains(ticket);
    }
}
