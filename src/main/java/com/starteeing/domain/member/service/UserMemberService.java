package com.starteeing.domain.member.service;

import com.starteeing.domain.member.dto.MemberLoginRequestDto;
import com.starteeing.domain.member.dto.MemberLoginResponseDto;
import com.starteeing.domain.member.dto.UserMemberRequestDto;
import com.starteeing.domain.member.entity.UserMember;
import com.starteeing.domain.member.exception.ExistMemberException;
import com.starteeing.domain.member.repository.MemberRepository;
import com.starteeing.domain.member.repository.UserMemberRepository;
import com.starteeing.golbal.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserMemberService {

    private final UserMemberRepository userMemberRepository;
    private final MemberRepository memberRepository;

    private final JwtProvider jwtProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 회원가입
     */
    public Long memberJoin(UserMemberRequestDto memberRequestDto) {
        validateDuplicateMember(memberRequestDto);

        UserMember userMember = memberRequestDto.toEntity(bCryptPasswordEncoder);

        UserMember savedMember = userMemberRepository.save(userMember);
        return savedMember.getId();
    }

    private void validateDuplicateMember(UserMemberRequestDto memberRequestDto) {
        if (memberRepository.existsByEmail(memberRequestDto.getEmail())) {
            throw new ExistMemberException();
        }
    }

    /**
     * 로그인
     */
    public MemberLoginResponseDto login(MemberLoginRequestDto loginRequestDto) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        return jwtProvider.createToken(authentication);
    }
    // TODO: 2022-05-26 RefreshToken은 어떻게 념겨주고 관리하는지?
    // TODO: 2022-05-28 spring security적용으로 깨진 테스트코드 복구하기 usermemberControllerTest부터
}