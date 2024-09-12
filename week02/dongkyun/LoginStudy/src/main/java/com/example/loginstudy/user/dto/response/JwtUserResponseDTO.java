package com.example.loginstudy.user.dto.response;

import com.example.loginstudy.user.domain.User;
import lombok.Builder;

@Builder
public record JwtUserResponseDTO(
        String username,
        String name,
        String email,
        String profile,
        String role
) {

    public static JwtUserResponseDTO fromEntity(User user) {
        return JwtUserResponseDTO.builder()
                .username(user.getUsername())
                .name(user.getName())
                .email(user.getEmail())
                .profile(user.getProfile())
                .role(user.getRole().name())
                .build();
    }

    public static JwtUserResponseDTO createFromJwt(String username, String role) {
        return JwtUserResponseDTO.builder()
                .username(username)
                .name(username)
                .role(role)
                .build();
    }
}
