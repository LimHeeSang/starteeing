package com.starteeing.domain.team.dto;

import com.starteeing.domain.team.entity.TeamUserMember;
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
                .map(teamUserMember -> TeamResponseDto.builder()
                        .teamId(teamUserMember.getTeam().getId())
                        .teamName(teamUserMember.getTeam().getName())
                        .build()
                ).collect(Collectors.toList());
    }

    @Builder
    private class TeamResponseDto {
        private Long teamId;

        private String teamName;
    }
}
