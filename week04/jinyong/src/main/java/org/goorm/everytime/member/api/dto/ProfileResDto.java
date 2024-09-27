package org.goorm.everytime.member.api.dto;

import org.goorm.everytime.member.domain.Member;

public record ProfileResDto(
        Long userId,
        String id,
        String name,
        String nickname,
        int year,
        String universityName
) {
    public static ProfileResDto of(Member member){
        return new ProfileResDto(
                member.getId(),
                member.getUsername(),
                member.getName(),
                member.getNickname(),
                member.getYear(),
                member.getUniversityName()
        );
    }
}
