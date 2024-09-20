package org.goorm.everytime.member.api.dto;

public record ProfileResDto(
        Long userId,
        String id,
        String name,
        String nickname,
        int year,
        String universityName
) {
}
