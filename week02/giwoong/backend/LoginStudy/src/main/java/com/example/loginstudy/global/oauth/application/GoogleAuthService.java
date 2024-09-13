package com.example.loginstudy.global.oauth.application;

import com.example.loginstudy.auth.api.dto.response.UserInfo;
import com.example.loginstudy.auth.application.AuthService;
import com.example.loginstudy.global.oauth.api.dto.response.GoogleTokenResponse;
import com.example.loginstudy.global.oauth.exception.OAuthException;
import com.example.loginstudy.member.domain.SocialType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional(readOnly = true)
public class GoogleAuthService implements AuthService {

    private static final String JWT_DELIMITER = "\\.";

    @Value("${oauth.google.client-id}")
    private String googleClientId;

    @Value("${oauth.google.client-secret}")
    private String googleClientSecret;

    @Value("${oauth.google.redirect-uri}")
    private String googleRedirectUri;

    @Value("${oauth.google.id-token-url}")
    private String googleIdTokenUrl;

    private final ObjectMapper objectMapper;

    private final RestTemplate restTemplate = new RestTemplate();

    public GoogleAuthService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public String getProvider() {
        return String.valueOf(SocialType.GOOGLE).toLowerCase();
    }

    @Override
    public String getIdToken(String authCode) {
        HttpEntity<MultiValueMap<String, String>> requestEntity = createRequestEntity(authCode);

        try {
            ResponseEntity<GoogleTokenResponse> responseTokenEntity = restTemplate.postForEntity(
                    googleIdTokenUrl,
                    requestEntity,
                    GoogleTokenResponse.class);

            return Objects.requireNonNull(responseTokenEntity.getBody()).getIdToken();
        } catch (RestClientException e) {
            throw new OAuthException();
        }
    }

    private HttpEntity<MultiValueMap<String, String>> createRequestEntity(String code) {
        return new HttpEntity<>(createRequestParams(code), createHttpHeaders());
    }

    private MultiValueMap<String, String> createRequestParams(String code) {
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();

        requestParams.add("code", code);
        requestParams.add("client_id", googleClientId);
        requestParams.add("client_secret", googleClientSecret);
        requestParams.add("redirect_uri", googleRedirectUri);
        requestParams.add("grant_type", "authorization_code");

        return requestParams;
    }

    private HttpHeaders createHttpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        return httpHeaders;
    }

    @Transactional
    @Override
    public UserInfo getUserInfo(String authCode) {
        String idToken = getIdToken(authCode);
        String decodePayload = getDecodePayload(idToken);

        try {
            return objectMapper.readValue(decodePayload, UserInfo.class);
        } catch (JsonProcessingException e) {
            throw new OAuthException("id 토큰을 읽을 수 없습니다.");
        }
    }

    private String getDecodePayload(String idToken) {
        String payload = getPayload(idToken);

        return new String(Base64.getUrlDecoder().decode(payload), StandardCharsets.UTF_8);
    }

    private String getPayload(String idToken) {
        return idToken.split(JWT_DELIMITER)[1];
    }

}
