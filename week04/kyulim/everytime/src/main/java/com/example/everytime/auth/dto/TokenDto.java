package com.example.everytime.auth.dto;

import lombok.Builder;

@Builder
public record TokenDto (

    String accessToken,
    String refreshToken
) {
}
