package org.goorm.everytime.auth.service;

import lombok.RequiredArgsConstructor;
import org.goorm.everytime.auth.domain.PrincipalDetails;
import org.goorm.everytime.member.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PrincipalUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByUsername(username)
                .map(PrincipalDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(username + " -> DB에서 찾을 수 없습니다."));
    }
}
