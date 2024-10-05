package org.goorm.everytime.member.service;

import lombok.RequiredArgsConstructor;
import org.goorm.everytime.member.api.dto.ProfileResDto;
import org.goorm.everytime.member.domain.Member;
import org.goorm.everytime.member.domain.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final MemberRepository memberRepository;

    public ProfileResDto getUserProfile(Principal principal) {
        Member userInfo = memberRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        return ProfileResDto.of(userInfo);
    }
}
