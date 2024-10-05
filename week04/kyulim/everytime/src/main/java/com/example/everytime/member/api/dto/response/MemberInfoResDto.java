package com.example.everytime.member.api.dto.response;

import com.example.everytime.member.domain.Member;

public record MemberInfoResDto(
        Long memberId,
        String id,
        String name,
        String nickName,
        int year,
        String universityName
) {
    public static MemberInfoResDto from(Member member) {
        return new MemberInfoResDto(
                member.getMemberId(),
                member.getId(),
                member.getName(),
                member.getNickName(),
                member.getYear(),
                member.getUniversityName() != null ? member.getUniversityName().getName() : null
        );
    }
}