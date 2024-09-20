package com.goormthon.everytime.app.service.auth.oauth;

import com.fasterxml.jackson.databind.JsonNode;
import com.goormthon.everytime.app.domain.image.Image;
import com.goormthon.everytime.app.domain.user.RoleType;
import com.goormthon.everytime.app.domain.user.User;
import com.goormthon.everytime.app.dto.auth.UserInfoDto;
import com.goormthon.everytime.app.dto.auth.resDto.AuthResDto;
import com.goormthon.everytime.app.repository.UserRepository;
import com.goormthon.everytime.app.service.auth.TokenRenewService;
import com.goormthon.everytime.global.exception.code.SuccessCode;
import com.goormthon.everytime.global.jwt.TokenProvider;
import com.goormthon.everytime.global.template.ApiResTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service("google")
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GoogleOAuthService implements OAuthService {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final TokenRenewService tokenRenewService;
    private final RestClient restClient;

    @Value("${oauth.google.client-id}")
    private String googleClientId;

    @Value("${oauth.google.secret-key}")
    private String googleSecretKey;

    @Value("${oauth.google.redirect-uri}")
    private String googleRedirectUri;

    @Override
    public String getUserInfoUrl() {
        return "https://www.googleapis.com/oauth2/v2/userinfo";
    }

    @Override
    public UserInfoDto parseUserInfo(JsonNode jsonNode) {
        String nickName = jsonNode.get("name").asText();
        String profileImage = jsonNode.get("picture").asText();
        return new UserInfoDto(nickName, profileImage);
    }

    @Override
    @Transactional
    public ApiResTemplate<AuthResDto> signUpOrLogin(String accessToken) {
        UserInfoDto userInfoDto = getUserInfo(accessToken, getUserInfoUrl(), restClient);

        return userRepository.findByNickName(userInfoDto.nickName())
                .map(existingUser -> generateToken(existingUser, SuccessCode.LOGIN_USER_SUCCESS, tokenProvider, tokenRenewService))
                .orElseGet(() -> {
                    Image realImage = Image.builder()
                            .imageUrl(userInfoDto.profileImage())
                            .build();

                    User newUser = User.builder()
                            .year("00")
                            .name(userInfoDto.nickName())
                            .nickName(userInfoDto.nickName())
                            .id(userInfoDto.nickName())
                            .password(null)
                            .roleType(RoleType.ROLE_USER)
                            .realImage(realImage)
                            .build();

                    userRepository.save(newUser);
                    return generateToken(newUser, SuccessCode.SIGNUP_USER_SUCCESS, tokenProvider, tokenRenewService);
                });
    }

    public ApiResTemplate<String> getAccessToken(String code) {
        String result = restClient.post()
                .uri("https://oauth2.googleapis.com/token")
                .body(Map.of(
                        "grant_type", "authorization_code",
                        "client_id", googleClientId,
                        "client_secret", googleSecretKey,
                        "redirect_uri", googleRedirectUri,
                        "code", code))
                .retrieve()
                .body(String.class);

        JsonNode jsonNode = parseJson(result);
        return ApiResTemplate.success(SuccessCode.GET_TOKEN_SUCCESS, jsonNode.get("access_token").asText());
    }
}
