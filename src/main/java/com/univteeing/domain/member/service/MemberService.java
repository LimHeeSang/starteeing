package com.univteeing.domain.member.service;

import com.univteeing.domain.member.dto.UserMemberRequestDto;
import com.univteeing.domain.member.entity.Member;
import com.univteeing.domain.member.entity.UserMember;
import com.univteeing.domain.member.repository.MemberRepository;
import com.univteeing.domain.member.repository.UserMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberService {

    private final UserMemberRepository userMemberRepository;
    private final MemberRepository memberRepository;

    /**
     * 회원가입
     */
    public Long memberJoin(UserMemberRequestDto memberRequestDto) {
        validateDuplicateMember(memberRequestDto);

        UserMember userMember = memberRequestDto.toEntity();

        UserMember savedMember = userMemberRepository.save(userMember);
        return savedMember.getId();
    }

    private void validateDuplicateMember(UserMemberRequestDto memberRequestDto) {
        if (memberRepository.existsByEmail(memberRequestDto.getEmail())) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
}