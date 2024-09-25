package com.example.everytime.member.api.dto.response;

public record MemberLoginResDto(
    String accessToken,
    String refreshToken
) {
}
