package com.starting.domain.team.dto;

import com.starting.domain.team.entity.TeamUserMember;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class TeamResponseDtos {

    private List<TeamResponseDto> result;

    @Builder
    public TeamResponseDtos(List<TeamUserMember> teamUserMembers) {
        this.result = mapToTeamResponseDtoList(teamUserMembers);
    }

    private List<TeamResponseDto> mapToTeamResponseDtoList(List<TeamUserMember> teamUserMembers) {
        return teamUserMembers.stream()
                .map(teamUserMember ->
                        new TeamResponseDto(teamUserMember.getTeam().getId(), teamUserMember.getTeam().getName())
                ).collect(Collectors.toList());
    }

    @Getter
    @AllArgsConstructor
    public static class TeamResponseDto {
        private Long teamId;

        private String teamName;
    }
}
