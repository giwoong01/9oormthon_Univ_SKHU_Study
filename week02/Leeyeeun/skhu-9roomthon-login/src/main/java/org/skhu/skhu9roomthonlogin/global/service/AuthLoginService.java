package org.skhu.skhu9roomthonlogin.global.service;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.skhu.skhu9roomthonlogin.global.dto.MemberInfo;
import org.skhu.skhu9roomthonlogin.global.dto.Token;
import org.skhu.skhu9roomthonlogin.global.jwt.TokenProvider;
import org.skhu.skhu9roomthonlogin.global.template.RspTemplate;
import org.skhu.skhu9roomthonlogin.member.domain.Member;
import org.skhu.skhu9roomthonlogin.member.domain.Role;
import org.skhu.skhu9roomthonlogin.member.domain.repository.MemberRepository;
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
public class AuthLoginService {

    @Value("${google.client-id}")
    private String googleClientId;

    @Value("${google.client-secret}")
    private String googleClientSecret;

    private final String GOOGLE_TOKEN_URL = "https://oauth2.googleapis.com/token";
    private final String GOOGLE_REDIRECT_URI = "http://localhost:8080/login/oauth2/code/google";

    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;

    public String getGoogleAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = Map.of(
                "code", code,
                "client_id", googleClientId,
                "client_secret", googleClientSecret,
                "redirect_uri", GOOGLE_REDIRECT_URI,
                "grant_type", "authorization_code"
        );

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(GOOGLE_TOKEN_URL, params, String.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            String json = responseEntity.getBody();
            Gson gson = new Gson();

            // Token 객체를 변환
            Token token = gson.fromJson(json, Token.class);
            return token.getAccessToken();
        }

        throw new RuntimeException("구글 엑세스 토큰을 가져오는데 실패했습니다.");
    }

    public RspTemplate<Token> loginOrSignUp(String googleAccessToken) {
        MemberInfo memberInfo = getMemberInfo(googleAccessToken);

        if (!memberInfo.getVerifiedEmail()) {
            return new RspTemplate<>(HttpStatus.UNAUTHORIZED, "이메일 인증이 되지 않은 유저입니다.");
        }

        Member member = memberRepository.findByEmail(memberInfo.getEmail()).orElseGet(() ->
                memberRepository.save(Member.builder()
                        .email(memberInfo.getEmail())
                        .nickname(memberInfo.getName())
                        .role(Role.ROLE_USER)
                        .build())
        );

        // JWT 생성
        String jwtToken = tokenProvider.generateToken(member.getEmail());
        Token token = new Token(jwtToken);

        return new RspTemplate<>(HttpStatus.OK, "로그인 성공", token);
    }

    public MemberInfo getMemberInfo(String accessToken) {
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
            return gson.fromJson(json, MemberInfo.class);
        }

        throw new RuntimeException("유저 정보를 가져오는데 실패했습니다.");
    }

//    public String getOauthRedirectURL() {
//        Map<String, Object> params = new HashMap<>();
//        params.put("scope", "email%20profile");
//        params.put("response_type", "code");
//        params.put("client_id", GOOGLE_CLIENT_ID);
//        params.put("redirect_uri", GOOGLE_REDIRECT_URI);
//
//        String parameterString = params.entrySet().stream()
//                .map(x -> x.getKey() + "=" + x.getValue())
//                .collect(Collectors.joining("&"));
//        return "https://accounts.google.com/o/oauth2/v2/auth" + "?" + parameterString;
//    }
}
