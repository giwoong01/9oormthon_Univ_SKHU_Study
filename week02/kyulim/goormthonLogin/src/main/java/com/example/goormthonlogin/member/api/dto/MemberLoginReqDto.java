package com.example.goormthonlogin.member.api.dto;

import lombok.Builder;

@Builder
public record MemberLoginReqDto(
        String email,
        String pwd
) {
}
