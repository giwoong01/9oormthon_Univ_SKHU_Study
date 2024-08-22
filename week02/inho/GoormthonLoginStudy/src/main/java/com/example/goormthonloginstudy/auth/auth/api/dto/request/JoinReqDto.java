package com.example.goormthonloginstudy.auth.auth.api.dto.request;

public record JoinReqDto(
        String email,
        String pwd,
        String name,
        String nickname
) {
}
