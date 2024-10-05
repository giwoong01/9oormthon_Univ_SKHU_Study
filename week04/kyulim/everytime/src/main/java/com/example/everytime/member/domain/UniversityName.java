package com.example.everytime.member.domain;

public enum UniversityName {

    SKHU("성공회대"),
    SNU("서울대"),
    KU("고려대"),
    YU("연세대");

    private final String name;

    UniversityName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
