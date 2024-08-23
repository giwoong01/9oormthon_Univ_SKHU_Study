package com.example.goormthonlogin.member.api.dto;

import com.example.goormthonlogin.member.domain.Member;
import lombok.Builder;

@Builder
public record MemberLoginResDto(
        String email,
        String nickname,
        String token
) {
    public static MemberLoginResDto of(Member member, String token)
    {
        return MemberLoginResDto.builder()
                .email(member.getEmail())
                .nickname(member.getNickname())
                .token(token)
                .build();
    }

}
