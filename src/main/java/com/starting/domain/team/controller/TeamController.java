package com.starting.domain.team.controller;

import com.starting.domain.team.dto.TeamCreateRequestDto;
import com.starting.domain.team.dto.TeamResponseDtos;
import com.starting.domain.team.service.TeamService;
import com.starting.global.response.ResponseService;
import com.starting.global.response.result.CommonResult;
import com.starting.global.response.result.SingleResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class TeamController {

    private final TeamService teamService;
    private final ResponseService responseService;

    /**
     * 팀 목록 조회
     */
    @GetMapping("/team/{memberId}")
    public ResponseEntity<SingleResult> getTeams(@PathVariable Long memberId) {
        TeamResponseDtos teamResponseDtos = teamService.getTeamList(memberId);
        return ResponseEntity.ok(responseService.getSingleResult(teamResponseDtos));
    }

    /**
     * 팀 생성
     */
    @PostMapping("/team/{memberId}")
    public ResponseEntity<SingleResult> createTeam(@PathVariable Long memberId, @RequestBody TeamCreateRequestDto createRequestDto) {
        Long teamId = teamService.createTeam(memberId, createRequestDto);
        return ResponseEntity.ok(responseService.getSingleResult(teamId));
    }

    /**
     * 팀 멤버 추가
     */
    @PostMapping("/team/{memberId}/{teamId}/{friendId}")
    public ResponseEntity<CommonResult> addTeamMember(
            @PathVariable Long memberId, @PathVariable Long teamId, @PathVariable Long friendId) {
        teamService.addTeamMember(memberId, teamId, friendId);
        return ResponseEntity.ok(responseService.getSuccessResult());
    }

    /**
     * 팀 탈퇴하기
     */
    @PostMapping("/team/{memberId}/{teamId}")
    public ResponseEntity<CommonResult> withDrawTeam(@PathVariable Long memberId, @PathVariable Long teamId) {
        teamService.withDrawTeam(memberId, teamId);
        return ResponseEntity.ok(responseService.getSuccessResult());
    }
}
