package com.goormthon.everytime.app.service.auth.oauth;

import com.fasterxml.jackson.databind.JsonNode;
import com.goormthon.everytime.app.domain.Image;
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
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@Service("kakao")
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class KakaoService implements OAuthService {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final TokenRenewService tokenRenewService;
    private final RestClient restClient;

    @Value("${oauth.kakao.client-id}")
    private String kakaoClientId;

    @Value("${oauth.kakao.secret-key}")
    private String kakaoSecretKey;

    @Value("${oauth.kakao.redirect-uri}")
    private String kakaoRedirectUri;

    @Override
    public ApiResTemplate<String> getAccessToken(String code) {
        String url = "https://kauth.kakao.com/oauth/token";
        MultiValueMap<String, String> request = createTokenRequest(code, kakaoClientId, kakaoSecretKey, kakaoRedirectUri);

        String result = restClient.post()
                .uri(url)
                .body(request)
                .retrieve()
                .body(String.class);

        JsonNode jsonNode = parseJson(result);
        return ApiResTemplate.success(SuccessCode.GET_TOKEN_SUCCESS, jsonNode.get("access_token").asText());
    }

    @Override
    public String getUserInfoUrl() {
        return "https://kapi.kakao.com/v2/user/me";
    }

    @Override
    public UserInfoDto parseUserInfo(JsonNode jsonNode) {
        String nickName = jsonNode.get("properties").get("nickname").asText();
        String profileImage = jsonNode.get("properties").get("profile_image").asText();
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
}