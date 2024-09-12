package com.example.loginstudy.auth.dto;

import com.example.loginstudy.user.domain.SocialType;
import java.util.Map;

public class GoogleResponse implements OAuth2Response {
    private static final String SUB_KEY = "sub";
    private static final String EMAIL_KEY = "email";
    private static final String NAME_KEY = "name";
    private static final String PICTURE_KEY = "picture";

    private final Map<String, Object> attribute;

    public GoogleResponse(Map<String, Object> attribute) {
        this.attribute = attribute;
    }

    @Override
    public String getProvider() {
        return SocialType.GOOGLE.name().toLowerCase();
    }

    @Override
    public String getProviderId() {
        return attribute.get(SUB_KEY).toString();
    }

    @Override
    public String getEmail() {
        return attribute.get(EMAIL_KEY).toString();
    }

    @Override
    public String getName() {
        return attribute.get(NAME_KEY).toString();
    }

    @Override
    public String getProfileImage() {
        return attribute.get(PICTURE_KEY).toString();
    }
}
