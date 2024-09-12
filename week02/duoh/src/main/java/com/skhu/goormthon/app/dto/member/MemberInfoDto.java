package com.skhu.goormthon.app.dto.member;

import com.skhu.goormthon.app.domain.member.LoginType;
import com.skhu.goormthon.app.domain.member.Member;
import lombok.Builder;

@Builder
public record MemberInfoDto(
        String name,
        String email,
        LoginType loginType
) {
    public static MemberInfoDto from(Member member) {
        return MemberInfoDto.builder()
                .name(member.getName())
                .email(member.getEmail())
                .loginType(member.getLoginType())
                .build();
    }

    public static MemberInfoDto of(String name, String email) {
        return MemberInfoDto.builder()
                .name(name)
                .email(email)
                .build();
    }
}
