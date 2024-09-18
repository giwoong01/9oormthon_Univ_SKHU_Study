package com.goormthon.everytime.app.dto.auth.reqDto;

import com.goormthon.everytime.app.domain.user.RoleType;
import com.goormthon.everytime.app.domain.user.User;
import com.goormthon.everytime.app.domain.user.University;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record SignUpReqDto(
        @NotBlank(message = "입학연도가 공백일 수 없습니다.")
        @Pattern(
                regexp = "^\\d{2}$",
                message = "입학연도는 숫자 2자리여야 합니다."
        )
        String year,

        @NotBlank(message = "대학명이 공백일 수 없습니다.")
        String universityName,

        @NotBlank(message = "이름이 공백일 수 없습니다.")
        String name,

        @NotBlank(message = "닉네임이 공백일 수 없습니다.")
        String nickName,

        @NotBlank(message = "이메일이 공백일 수 없습니다.")
        @Pattern(
                regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
                message = "이메일을 다시 확인해주세요."
        )
        String email,

        @NotBlank(message = "아이디가 공백일 수 없습니다.")
        String id,

        @NotBlank(message = "비밀번호가 공백일 수 없습니다.")
        @Pattern(
                regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]+$",
                message = "비밀번호는 대소문자, 특수문자, 숫자가 포함되어야 합니다."
        )
        String password,

        @NotBlank(message = "비밀번호 재확인이 공백일 수 없습니다.")
        String checkPassword
) {
    public User toEntity(String encodedPassword, University university) {
        return User.builder()
                .year(this.year)
                .university(university)
                .name(this.name)
                .nickName(this.nickName)
                .email(this.email)
                .id(this.id)
                .password(encodedPassword)
                .roleType(RoleType.ROLE_USER)
                .build();
    }
}
