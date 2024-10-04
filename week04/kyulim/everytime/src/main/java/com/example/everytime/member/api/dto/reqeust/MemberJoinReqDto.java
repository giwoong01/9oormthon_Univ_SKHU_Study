package com.example.everytime.member.api.dto.reqeust;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record MemberJoinReqDto(
        @Positive(message = "입학연도가 공백일 수 없습니다.")
        int year,

        @NotBlank(message = "학교명이 공백일 수 없습니다.")
        String universityName,

        @NotBlank(message = "이름이 공백일 수 없습니다.")
        String name,

        @NotBlank(message = "닉네임이 공백일 수 없습니다.")
        String nickName,

        @Email
        @NotBlank(message = "이메일이 공백일 수 없습니다.")
        String email,

        @NotBlank(message = "아이디가 공백일 수 없습니다.")
        String id,

        @NotBlank(message = "비밀번호가 공백일 수 없습니다.")
        @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
                message = "비밀번호를 다시 확인해주세요")
        String password,

        @NotBlank(message = "확인 비밀번호가 공백일 수 없습니다.")
        String checkPassword
) {
}
