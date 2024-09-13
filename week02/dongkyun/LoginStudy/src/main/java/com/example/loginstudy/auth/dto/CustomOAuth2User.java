package com.example.loginstudy.auth.dto;

import com.example.loginstudy.user.dto.response.JwtUserResponseDTO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomOAuth2User implements OAuth2User {
    private final JwtUserResponseDTO userDTO;

    public CustomOAuth2User(JwtUserResponseDTO userDTO) {
        this.userDTO = userDTO;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null; // 필요시 구현
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add((GrantedAuthority) userDTO::role);
        return collection;
    }

    @Override
    public String getName() {
        return userDTO.name();
    }

    public String getUsername() {
        return userDTO.username();
    }

}
