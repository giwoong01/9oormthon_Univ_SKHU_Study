package com.example.goormthonloginstudy.auth.auth.application;

import com.example.goormthonloginstudy.auth.auth.api.dto.response.UserInfo;
import com.fasterxml.jackson.databind.JsonNode;

public interface AuthService {
    UserInfo getUserInfo(String authCode);

    String getProvider();

    JsonNode getIdToken(String code);
}
