package com.starteeing.domain.team.dto;

import com.starteeing.domain.team.entity.TeamUserMember;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

public class TeamListResponseDto {

    private List<TeamResponseDto> result;

    @Builder
    public TeamListResponseDto(List<TeamUserMember> teamUserMembers) {
        this.result = mapToTeamResponseDtoList(teamUserMembers);
    }

    private List<TeamResponseDto> mapToTeamResponseDtoList(List<TeamUserMember> teamUserMembers) {
        return teamUserMembers.stream()
                .map(teamUserMember ->
                        new TeamResponseDto(teamUserMember.getTeam().getId(), teamUserMember.getTeam().getName())
                ).collect(Collectors.toList());
    }

    @AllArgsConstructor
    private class TeamResponseDto {
        private Long teamId;

        private String teamName;
    }
}
