package org.skhu.everytime.user.dto;

public record UserProfileDto(
        Long userId,
        String email,
        String name,
        String nickname,
        int year,
        String universityName
) {
}
