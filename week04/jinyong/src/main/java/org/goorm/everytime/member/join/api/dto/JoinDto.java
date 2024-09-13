package org.goorm.everytime.member.join.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record JoinDto(
        @NotBlank String username,
        @NotBlank String password,
        @NotBlank String checkPassword,
        @NotBlank String nickname,
        @NotBlank @Email String email,
        @Min(20) int year,
        @NotBlank String universityName,
        @NotBlank String name
) {
}