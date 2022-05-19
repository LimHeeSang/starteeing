package com.starteeing.golbal.security;

import com.starteeing.domain.member.entity.Member;
import com.starteeing.domain.member.repository.MemberRepository;
import com.starteeing.domain.member.repository.UserMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("등록된 사용자가 아닙니다."));

        return createUser(member);
    }

    private User createUser(Member member) {
        SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(member.getMemberRole().toString());
        return new User(member.getEmail(), member.getPassword(), Collections.singleton(grantedAuthority));
    }
}
