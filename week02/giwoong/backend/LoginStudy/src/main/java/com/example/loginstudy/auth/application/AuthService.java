package com.example.loginstudy.auth.application;

import com.example.loginstudy.auth.api.dto.response.UserInfo;

public interface AuthService {
    String getIdToken(String authCode);

    UserInfo getUserInfo(String authCode);

    String getProvider();
}
