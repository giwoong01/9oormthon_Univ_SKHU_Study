package com.example.goormthonlogin.member.api.dto;

public record MemberSaveReqDto(
        String email,
        String pwd,
        String nickname
) {
}
