package com.starting.global.security;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final String DEFAULT_PASSWORD_OF_OAUTH2 = "";
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-z]+$";

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (isEmailRegex(username)) {
            Member emailRegisterMember = memberRepository.findByEmailWithMemberRoles(username).orElseThrow(NotExistMemberException::new);
            return createUserForEmail(emailRegisterMember);
        }

        Member oAuthRegisterMember = memberRepository.findByUserIdWithMemberRoles(username).orElseThrow(NotExistMemberException::new);
        return createUserForOauth(oAuthRegisterMember);
    }

    private boolean isEmailRegex(String userName) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(userName);
        return matcher.matches();
    }

    private User createUserForOauth(Member member) {
        return new User(member.getUserId(), DEFAULT_PASSWORD_OF_OAUTH2, mapToAuthorities(member.mapToMemberRoleEnum()));
    }

    private User createUserForEmail(Member member) {
        return new User(member.getUserId(), member.getPassword(), mapToAuthorities(member.mapToMemberRoleEnum()));
    }

    private List<GrantedAuthority> mapToAuthorities(List<MemberRoleEnum> memberRoleEnums) {
        return memberRoleEnums.stream()
                .map(memberRole -> new SimpleGrantedAuthority(memberRole.toString()))
                .collect(Collectors.toList());
    }
}