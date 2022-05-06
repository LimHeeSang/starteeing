package com.univteeing.domain.member.service;

import com.univteeing.domain.member.dto.UserMemberRequestDto;
import com.univteeing.domain.member.entity.Member;
import com.univteeing.domain.member.entity.UserMember;
import com.univteeing.domain.member.repository.UserMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberService {

    private final UserMemberRepository userMemberRepository;

    /**
     * 회원가입
     */
    public Long memberJoin(UserMemberRequestDto memberRequestDto) {
        UserMember userMember = memberRequestDto.toEntity();

        UserMember savedMember = userMemberRepository.save(userMember);
        return savedMember.getId();
    }
}