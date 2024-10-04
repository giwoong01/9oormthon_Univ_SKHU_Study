package org.skhu.everytime.user.dto;


public record UserSignUpDto(
        int year,             // 입학연도
        String universityName, // 대학교 이름
        String name,         // 이름
        String nickName,     // 닉네임
        String email,        // 이메일
        String id,           // 아이디
        String password,      // 비밀번호
        String checkPassword  // 비밀번호 확인
) {
}

