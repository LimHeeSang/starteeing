package com.starting.domain.meeting.dto;

import com.starting.domain.meeting.entity.Match;
import com.starting.domain.team.entity.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MatchListResponseDto {

    private List<List<MatchResponseDto>> result;

    public MatchListResponseDto(Match match) {
        result = new ArrayList<>();
        List<MatchResponseDto> matchResponseDtos = match.getTeams().stream().map(MatchResponseDto::new).collect(Collectors.toList());
        result.add(matchResponseDtos);
    }

    public class MatchResponseDto {
        private String teamName;

        private int memberNumber;

        private String gender;

        public MatchResponseDto(Team team) {
            teamName = team.getName();
            memberNumber = team.getUserMemberCount();
            gender = team.getTeamGender().name();
        }
    }
}
