package com.starting.domain.team.service;

import com.starting.domain.member.entity.Member;
import com.starting.domain.member.entity.UserMember;
import com.starting.domain.member.exception.ExistMemberException;
import com.starting.domain.member.exception.NotExistMemberException;
import com.starting.domain.member.repository.MemberRepository;
import com.starting.domain.team.dto.TeamCreateRequestDto;
import com.starting.domain.team.dto.TeamListResponseDto;
import com.starting.domain.team.entity.Team;
import com.starting.domain.team.entity.TeamUserMember;
import com.starting.domain.team.repository.TeamRepository;
import com.starting.domain.team.repository.TeamUserMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final TeamUserMemberRepository teamUserMemberRepository;
    private final MemberRepository memberRepository;

    /**
     * 팀 만들기
     */
    public Long createTeam(Long id, TeamCreateRequestDto createRequestDto) {
        memberRepository.findById(id).orElseThrow(NotExistMemberException::new);

        List<Member> members = memberRepository.findAllById(createRequestDto.getMemberIds());
        Team createdTeam = Team.builder()
                .teamName(createRequestDto.getTeamName())
                .members(members)
                .build();

        Team savedTeam = teamRepository.save(createdTeam);
        return savedTeam.getId();
    }

    /**
     * 팀 탈퇴하기
     */
    public void withDrawTeam(Long memberId, Long teamId) {
        Member findMember = memberRepository.findById(memberId).orElseThrow(NotExistMemberException::new);
        Team findTeam = teamRepository.findByIdWithMembers(teamId).orElseThrow(IllegalArgumentException::new);

        TeamUserMember teamUserMember = teamUserMemberRepository.findByTeamAndUserMember(findTeam, (UserMember) findMember)
                .orElseThrow(NotExistMemberException::new);
        findTeam.withDrawMember(teamUserMember);
    }

    /**
     * 팀 멤버 추가
     */
    public void addTeamMember(Long toMemberId, Long teamId, Long fromMemberId) {
        memberRepository.findById(toMemberId).orElseThrow(NotExistMemberException::new);
        Team findTeam = teamRepository.findByIdWithMembers(teamId).orElseThrow(IllegalArgumentException::new);
        Member fromMember = memberRepository.findById(fromMemberId).orElseThrow(NotExistMemberException::new);

        if (teamUserMemberRepository.existsByTeamAndUserMember(findTeam, (UserMember) fromMember)) {
            throw new ExistMemberException();
        }
        findTeam.addTeamMember(fromMember);
    }

    /**
     * 팀 목록 조회하기
     */
    public TeamListResponseDto getTeamList(Long memberId) {
        Member findMember = memberRepository.findById(memberId).orElseThrow(NotExistMemberException::new);
        List<TeamUserMember> teamUserMembers = teamUserMemberRepository.findAllByUserMember((UserMember) findMember);

        return TeamListResponseDto.builder().teamUserMembers(teamUserMembers).build();
    }
}