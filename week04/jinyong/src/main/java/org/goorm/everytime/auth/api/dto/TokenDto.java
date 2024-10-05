package org.goorm.everytime.auth.api.dto;

import lombok.Builder;

@Builder
public record TokenDto(String grantType, String accessToken, String refreshToken) {}