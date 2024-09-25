package com.example.everytime.member.api.dto.response;

import com.example.everytime.member.domain.Member;
import com.example.everytime.universityName.api.dto.UniversityNameDto;
import com.example.everytime.universityName.domain.UniversityName;

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