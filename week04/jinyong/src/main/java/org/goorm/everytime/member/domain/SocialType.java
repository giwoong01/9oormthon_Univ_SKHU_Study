package org.goorm.everytime.member.domain;

public enum SocialType {
    GOOGLE("google"),
    KAKAO("kakao"),
    SELF("self");

    SocialType(String type) {
    }

    public static SocialType of(String type) {
        if (type.equals("google")) {
            return GOOGLE;
        } else if (type.equals("kakao")) {
            return KAKAO;
        } else {
            return SELF;
        }
    }
}
