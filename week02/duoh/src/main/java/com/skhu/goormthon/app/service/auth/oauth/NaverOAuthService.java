package com.skhu.goormthon.app.service.auth.oauth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skhu.goormthon.app.domain.member.LoginType;
import com.skhu.goormthon.app.dto.member.MemberInfoDto;
import com.skhu.goormthon.app.repository.MemberRepository;
import com.skhu.goormthon.app.service.auth.TokenRenewService;
import com.skhu.goormthon.global.template.ApiResTemplate;
import com.skhu.goormthon.global.jwt.TokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@Service
public class NaverOAuthService extends BaseOAuthService {

    private static final String NAVER_MEMBER_INFO_URL = "https://openapi.naver.com/v1/nid/me";
    private static final String NAVER_TOKEN_URL = "https://nid.naver.com/oauth2.0/token";

    @Value("${oauth.naver.client-id}")
    private String NAVER_CLIENT_ID;

    @Value("${oauth.naver.client-secret}")
    private String NAVER_CLIENT_SECRET;

    @Value("${oauth.naver.redirect-uri}")
    private String NAVER_REDIRECT_URI;

    public NaverOAuthService(MemberRepository memberRepository,
                             TokenProvider tokenProvider,
                             TokenRenewService tokenRenewService,
                             RestClient restClient,
                             ObjectMapper objectMapper) {
        super(memberRepository, tokenProvider, tokenRenewService, restClient, objectMapper);
    }

    @Override
    protected String getMemberInfoUrl() {
        return NAVER_MEMBER_INFO_URL;
    }

    @Override
    protected LoginType getLoginType() {
        return LoginType.NAVER_LOGIN;
    }

    @Override
    protected MemberInfoDto parseMemberInfo(JsonNode jsonNode, String accessToken) {
        JsonNode response = jsonNode.get("response");
        String email = response.get("email").asText();
        String name = response.get("name").asText();

        return MemberInfoDto.of(name, email);
    }

    public ApiResTemplate<String> getAccessToken(String code, String state) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", NAVER_CLIENT_ID);
        params.add("client_secret", NAVER_CLIENT_SECRET);
        params.add("redirect_uri", NAVER_REDIRECT_URI);
        params.add("code", code);
        params.add("state", state);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        return getAccessToken(NAVER_TOKEN_URL, requestEntity);
    }
}
