package com.example.goormthonlogin.auth.application;


import com.example.goormthonlogin.auth.dto.MemberInfo;
import com.example.goormthonlogin.auth.dto.Token;
import com.example.goormthonlogin.auth.jwt.TokenProvider;
import com.example.goormthonlogin.member.api.dto.MemberLoginReqDto;
import com.example.goormthonlogin.member.domain.Member;
import com.example.goormthonlogin.member.domain.Role;
import com.example.goormthonlogin.member.domain.repository.MemberRepository;
import com.example.goormthonlogin.member.exeption.InvalidMemberException;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GoogleLoginService {
    @Value("${client-id}")
    private String GOOGLE_CLIENT_ID;

    @Value("${client-secret}")
    private String GOOGLE_CLIENT_SECRET;

    private final String GOOGLE_TOKEN_URL = "https://oauth2.googleapis.com/token";
    private final String GOOGLE_REDIRECT_URI = "http://localhost:8080/login/oauth2/code/google";

    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;

    public String getGoogleAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = Map.of(
                "code", code,
                "scppe", "https://www/googleapis.com/auth/userInfo.profile https://www/googleapis.com/auth/userInfo.email",
                "client_id", GOOGLE_CLIENT_ID,
                "client_secret", GOOGLE_CLIENT_SECRET,
                "redirect_uri", GOOGLE_REDIRECT_URI,
                "grant_type", "authorization_code"
        );

        // 구글 토큰 URL로 post 요청 보내서 액세스 토큰 가져오는 로직
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(GOOGLE_TOKEN_URL, params, String.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            String json = responseEntity.getBody();
            Gson gson = new Gson();

            // Json응답을 token 객체로 변환해서 액세스 토큰 반환
            return gson.fromJson(json, Token.class)
                    .getAccessToken();
        }

        // 요청이 실패 예외
        throw new RuntimeException("구글 액세스 토큰을 가져오는데 실패하였습니다.");
    }

    // 로그인 회원가입
    public Token loginOrSignUp(String googleAccessToken) {
        MemberInfo memberInfo = getUserInfo(googleAccessToken);

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

    // 구글 액세스 토큰으로 사용자 정보 가져오기
    public MemberInfo getUserInfo(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://www.googleapis.com/oauth2/v2/userinfo?access_token=" + accessToken;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        RequestEntity<Void> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, URI.create(url));
        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            String json = responseEntity.getBody();
            Gson gson = new Gson();
            // json 응답 UserInfo 객체로 변환해서 반환
            return gson.fromJson(json,MemberInfo.class);
        }

        // 요청 실패 예외
        throw new RuntimeException("유저 정보를 가져오는데 실패했습니다.");
    }
}
