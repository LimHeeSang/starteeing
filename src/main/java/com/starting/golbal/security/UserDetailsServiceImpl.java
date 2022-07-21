package com.starting.golbal.security;

import com.starting.domain.member.entity.Member;
import com.starting.domain.member.entity.MemberRoleEnum;
import com.starting.domain.member.exception.NotExistMemberException;
import com.starting.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmailWithMemberRoles(email).orElseThrow(NotExistMemberException::new);
        return createUser(member);
    }

    private User createUser(Member member) {
        return new User(member.getEmail(), member.getPassword(), mapToAuthorities(member.mapToMemberRoleEnum()));
    }

    private List<GrantedAuthority> mapToAuthorities(List<MemberRoleEnum> memberRoleEnums) {
        return memberRoleEnums.stream()
                .map(memberRole -> new SimpleGrantedAuthority(memberRole.toString()))
                .collect(Collectors.toList());
    }
}