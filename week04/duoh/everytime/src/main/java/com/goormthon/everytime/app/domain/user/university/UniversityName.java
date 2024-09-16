package com.goormthon.everytime.app.domain.user.university;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UniversityName {
    SKHU("성공회대"),
    SNU("서울대"),
    KU("고려대"),
    SSU("숭실대");

    private final String displayName;
}
