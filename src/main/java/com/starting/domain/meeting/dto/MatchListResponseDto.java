package com.starting.domain.meeting.dto;

import com.starting.domain.meeting.entity.Matches;
import com.starting.domain.team.entity.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MatchListResponseDto {

    private List<List<MatchResponseDto>> result;

    public MatchListResponseDto(Matches matches) {
        result = new ArrayList<>();
        List<MatchResponseDto> matchResponseDtos = matches.getTeams().stream().map(MatchResponseDto::new).collect(Collectors.toList());
        result.add(matchResponseDtos);
    }

    public static class MatchResponseDto {
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
