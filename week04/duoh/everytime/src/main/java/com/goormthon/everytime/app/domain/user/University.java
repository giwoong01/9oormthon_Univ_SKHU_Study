package com.goormthon.everytime.app.domain.user;

import com.goormthon.everytime.global.exception.CustomException;
import com.goormthon.everytime.global.exception.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum University {
    SKHU("성공회대"),
    SNU("서울대"),
    KU("고려대"),
    SSU("숭실대");

    private final String displayName;

    public static University fromDisplayName(String displayName) {
        return Arrays.stream(University.values())
                .filter(universityName -> universityName.displayName.equals(displayName))
                .findFirst()
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_UNIVERSITY_NAME, ErrorCode.INVALID_UNIVERSITY_NAME.getMessage()));
    }
}
