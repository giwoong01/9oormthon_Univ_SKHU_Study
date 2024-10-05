package com.goormthon.everytime.app.dto.auth.reqDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record LoginReqDto(
        @NotBlank(message = "아이디가 공백일 수 없습니다.")
        String id,

        @NotBlank(message = "비밀번호가 공백일 수 없습니다.")
        @Pattern(
                regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]+$",
                message = "비밀번호는 대소문자, 특수문자, 숫자가 포함되어야 합니다."
        )
        String password
) {
}
