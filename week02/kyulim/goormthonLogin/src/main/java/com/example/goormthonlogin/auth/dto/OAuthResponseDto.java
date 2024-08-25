package com.example.goormthonlogin.auth.dto;

public class OAuthResponseDto {
    private Long id;
    private String accessToken;
    private String refreshToken;
    private Boolean isMember;

    public OAuthResponseDto(Long id, String accessToken, String refreshToken, Boolean isMember) {
        this.id = id;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.isMember = isMember;
    }
}
