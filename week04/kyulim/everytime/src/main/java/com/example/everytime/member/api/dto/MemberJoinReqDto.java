package com.example.everytime.member.api.dto;

import com.example.everytime.School.domain.School;

public record MemberJoinReqDto(
        int year,
        School school,
        String name,
        String nickName,
        String email,
        String id,
        String password,
        String checkPassword
) {
}
