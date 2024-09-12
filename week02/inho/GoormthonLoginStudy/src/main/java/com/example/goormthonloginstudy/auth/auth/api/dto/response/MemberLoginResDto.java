package com.example.goormthonloginstudy.auth.auth.api.dto.response;


import com.example.goormthonloginstudy.member.domain.Member;
import lombok.Builder;

@Builder
public record MemberLoginResDto(
        Member findMember
) {
    public static MemberLoginResDto from(Member member) {
        return MemberLoginResDto.builder()
                .findMember(member)
                .build();
    }
}
