package com.skhu.goormthon.app.service.auth.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skhu.goormthon.app.domain.member.LoginType;
import com.skhu.goormthon.app.dto.member.MemberInfoDto;
import com.skhu.goormthon.app.repository.MemberRepository;
import com.skhu.goormthon.app.service.auth.TokenRenewService;
import com.skhu.goormthon.global.exception.CustomException;
import com.skhu.goormthon.global.exception.code.ErrorCode;
import com.skhu.goormthon.global.template.ApiResTemplate;
import com.skhu.goormthon.global.jwt.TokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class GithubOAuthService extends BaseOAuthService {

    private static final String GITHUB_MEMBER_INFO_URL = "https://api.github.com/user";
    private static final String GITHUB_TOKEN_URL = "https://github.com/login/oauth/access_token";
    private static final String GITHUB_EMAIL_URL = "https://api.github.com/user/emails";

    @Value("${oauth.github.client-id}")
    private String GITHUB_CLIENT_ID;

    @Value("${oauth.github.client-secret}")
    private String GITHUB_CLIENT_SECRET;

    @Value("${oauth.github.redirect-uri}")
    private String GITHUB_REDIRECT_URI;

    public GithubOAuthService(MemberRepository memberRepository,
                              TokenProvider tokenProvider,
                              TokenRenewService tokenRenewService,
                              RestClient restClient,
                              ObjectMapper objectMapper) {
        super(memberRepository, tokenProvider, tokenRenewService, restClient, objectMapper);
    }

    @Override
    protected String getMemberInfoUrl() {
        return GITHUB_MEMBER_INFO_URL;
    }

    @Override
    protected LoginType getLoginType() {
        return LoginType.GITHUB_LOGIN;
    }

    @Override
    protected MemberInfoDto parseMemberInfo(JsonNode jsonNode, String accessToken) {
        String name = jsonNode.has("name") && !jsonNode.get("name").isNull()
                ? jsonNode.get("name").asText()
                : "GitHub Member";

        String email = jsonNode.has("email") && !jsonNode.get("email").isNull()
                ? jsonNode.get("email").asText()
                : getPrimaryEmail(accessToken);

        if (email == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_EMAIL_EXCEPTION, ErrorCode.NOT_FOUND_EMAIL_EXCEPTION.getMessage());
        }

        return MemberInfoDto.of(name, email);
    }

    private String getPrimaryEmail(String accessToken) {
        String response = restClient.get()
                .uri(GITHUB_EMAIL_URL)
                .headers(headers -> headers.set("Authorization", "Bearer " + accessToken))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, responseEntity) -> {
                    throw new CustomException(ErrorCode.NOT_FOUND_EMAIL_EXCEPTION, ErrorCode.NOT_FOUND_EMAIL_EXCEPTION.getMessage());
                })
                .onStatus(HttpStatusCode::is5xxServerError, (request, responseEntity) -> {
                    throw new CustomException(ErrorCode.INTERNAL_SERVER_EXCEPTION, ErrorCode.INTERNAL_SERVER_EXCEPTION.getMessage());
                })
                .body(String.class);

        try {
            JsonNode emails = objectMapper.readTree(response);
            for (JsonNode emailNode : emails) {
                if (emailNode.has("primary") && emailNode.get("primary").asBoolean() &&
                        emailNode.has("verified") && emailNode.get("verified").asBoolean()) {
                    return emailNode.get("email").asText();
                }
            }
        } catch (JsonProcessingException e) {
            throw new CustomException(ErrorCode.JSON_SYNTAX_EXCEPTION, ErrorCode.JSON_SYNTAX_EXCEPTION.getMessage());
        }

        return null;
    }

    public ApiResTemplate<String> getAccessToken(String code) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", GITHUB_CLIENT_ID);
        params.add("client_secret", GITHUB_CLIENT_SECRET);
        params.add("redirect_uri", GITHUB_REDIRECT_URI);
        params.add("code", code);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        return getAccessToken(GITHUB_TOKEN_URL, requestEntity);
    }
}
