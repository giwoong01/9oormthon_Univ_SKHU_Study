package com.example.loginstudy.user.dto.response;

import com.example.loginstudy.user.domain.User;
import lombok.Builder;

@Builder
public record LoginResponseDTO(
        User user
) {
    public static LoginResponseDTO from(User user) {
        return LoginResponseDTO.builder()
                .user(user)
                .build();
    }
}
