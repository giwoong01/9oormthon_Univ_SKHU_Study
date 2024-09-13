package com.example.loginstudy.user.dto.response;

import com.example.loginstudy.user.domain.User;
import lombok.Builder;

@Builder
public record UserResponseDTO(
        String name,
        String email,
        String password,
        String profile,
        String role
) {
    public static UserResponseDTO of(User user) {
        return UserResponseDTO.builder()
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .profile(user.getProfile())
                .role(user.getRole().name())
                .build();
    }
}
