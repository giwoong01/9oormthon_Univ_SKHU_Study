package com.goormthon.everytime.app.service.auth.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goormthon.everytime.app.domain.user.User;
import com.goormthon.everytime.app.dto.auth.UserInfoDto;
import com.goormthon.everytime.app.dto.auth.resDto.AuthResDto;
import com.goormthon.everytime.app.service.auth.TokenRenewService;
import com.goormthon.everytime.global.exception.CustomException;
import com.goormthon.everytime.global.exception.code.ErrorCode;
import com.goormthon.everytime.global.exception.code.SuccessCode;
import com.goormthon.everytime.global.jwt.TokenProvider;
import com.goormthon.everytime.global.template.ApiResTemplate;
import org.springframework.web.client.RestClient;

public interface OAuthService {

    ApiResTemplate<AuthResDto> signUpOrLogin(String accessToken);
    ApiResTemplate<String> getAccessToken(String code);
    String getUserInfoUrl();
    UserInfoDto parseUserInfo(JsonNode jsonNode);

    default ApiResTemplate<AuthResDto> generateToken(User user, SuccessCode successCode, TokenProvider tokenProvider, TokenRenewService tokenRenewService) {
        String accessToken = tokenProvider.createAccessToken(user);
        String refreshToken = tokenProvider.createRefreshToken(user);
        tokenRenewService.saveRefreshToken(refreshToken, user.getUserId());

        return ApiResTemplate.success(successCode, AuthResDto.of(accessToken, refreshToken));
    }

    default UserInfoDto getUserInfo(String accessToken, String userInfoUrl, RestClient restClient) {
        String result = restClient.get()
                .uri(userInfoUrl)
                .headers(headers -> headers.set("Authorization", "Bearer " + accessToken))
                .retrieve()
                .body(String.class);

        JsonNode jsonNode = parseJson(result);
        return parseUserInfo(jsonNode);
    }

    default JsonNode parseJson(String jsonString) {
        try {
            return new ObjectMapper().readTree(jsonString);
        } catch (JsonProcessingException e) {
            throw new CustomException(ErrorCode.JSON_PARSE_ERROR, ErrorCode.JSON_PARSE_ERROR.getMessage());
        }
    }
}
