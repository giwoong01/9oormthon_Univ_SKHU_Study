package com.example.loginstudy.user.dto.request;

import com.example.loginstudy.auth.dto.OAuth2Response;
import com.example.loginstudy.user.domain.Role;
import com.example.loginstudy.user.domain.User;
import lombok.Builder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Builder
public record UserCreateRequestDTO(
        String username,
        String name,
        String email,
        String password,
        String profile
) {
    public User toEntityForOauth() {
        return User.builder()
                .username(username)
                .name(name)
                .email(email)
                .password("oauth 계정")
                .role(Role.ROLE_USER)
                .profile(profile)
                .build();
    }

    public User toEntity(PasswordEncoder passwordEncoder) {
        return User.builder()
                .username("자체 로그인")
                .name(name)
                .email(email)
                .password(passwordEncode(passwordEncoder))
                .role(Role.ROLE_USER)
                .profile(profile)
                .build();
    }

    public static UserCreateRequestDTO fromOAuth2Response(OAuth2Response oAuth2Response, String username) {
        return UserCreateRequestDTO.builder()
                .username(username)
                .name(oAuth2Response.getName())
                .email(oAuth2Response.getEmail())
                .profile(oAuth2Response.getProfileImage())
                .build();
    }

    private String passwordEncode(PasswordEncoder passwordEncoder) {

        return passwordEncoder.encode(password);
    }
}
