package com.example.goormthonlogin.auth.application;

import com.example.goormthonlogin.auth.dto.MemberInfo;
import com.example.goormthonlogin.auth.dto.Token;
import com.example.goormthonlogin.auth.jwt.TokenProvider;
import com.example.goormthonlogin.member.domain.Member;
import com.example.goormthonlogin.member.domain.Role;
import com.example.goormthonlogin.member.domain.repository.MemberRepository;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class NaverLoginService {

    @Value("${naver.client-id}")
    private String NAVER_CLIENT_ID;

    @Value("${naver.client-secret}")
    private String NAVER_CLIENT_SECRET;

    private final String NAVER_TOKEN_URL = "https://nid.naver.com/oauth2.0/token";
    private final String NAVER_USER_INFO_URL = "https://openapi.naver.com/v1/nid/me";

    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;

    public String getNaverAccessToken(String code, String state) {
        RestTemplate restTemplate = new RestTemplate();
        String url = NAVER_TOKEN_URL + "?grant_type=authorization_code" +
                "&client_id=" + NAVER_CLIENT_ID +
                "&client_secret=" + NAVER_CLIENT_SECRET +
                "&code=" + code +
                "&state=" + state;

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            String json = responseEntity.getBody();
            Gson gson = new Gson();

            return gson.fromJson(json, Token.class)
                    .getAccessToken();
        }

        throw new RuntimeException("네이버 액세스 토큰을 가져오는데 실패하였습니다.");
    }

    public Token loginOrSignUp(String naverAccessToken) {
        MemberInfo memberInfo = getUserInfo(naverAccessToken);

        if (!memberInfo.getVerifiedEmail()) {
            throw new RuntimeException("이메일 인증이 되지 않은 유저입니다.");
        }

        Member member = memberRepository.findByEmail(memberInfo.getEmail()).orElseGet(() ->
                memberRepository.save(Member.builder()
                        .email(memberInfo.getEmail())
                        .name(memberInfo.getName())
                        .role(Role.ROLE_USER)
                        .build())
        );

        return tokenProvider.createToken(member);
    }

    public MemberInfo getUserInfo(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        RequestEntity<Void> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, URI.create(NAVER_USER_INFO_URL));
        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            String json = responseEntity.getBody();
            Gson gson = new Gson();
            return gson.fromJson(json, MemberInfo.class);
        }

        throw new RuntimeException("유저 정보를 가져오는데 실패했습니다.");
    }
}
