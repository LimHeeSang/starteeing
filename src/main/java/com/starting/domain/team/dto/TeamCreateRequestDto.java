package com.starting.domain.team.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class TeamCreateRequestDto {

    private String teamName;

    private List<String> memberIds;

    public List<Long> getMemberIds() {
        return memberIds.stream()
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }
}