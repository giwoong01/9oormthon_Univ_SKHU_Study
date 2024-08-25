package com.skhu.goormthon.app.dto.auth.reqDto;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenReqDto(
        @NotBlank(message = "refreshToken은 필수 입력 항목입니다.")
        String refreshToken
) {
}
