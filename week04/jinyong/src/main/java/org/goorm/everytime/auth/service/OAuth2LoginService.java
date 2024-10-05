package org.goorm.everytime.auth.service;

import lombok.RequiredArgsConstructor;
import org.goorm.everytime.auth.api.dto.GoogleTokenDto;
import org.goorm.everytime.auth.api.dto.KakaoTokenDto;
import org.goorm.everytime.auth.api.dto.TokenDto;
import org.goorm.everytime.auth.domain.GoogleUserInfo;
import org.goorm.everytime.auth.domain.KakaoUserInfo;
import org.goorm.everytime.auth.domain.OAuth2UserInfo;
import org.goorm.everytime.auth.domain.SocialType;
import org.goorm.everytime.auth.jwt.TokenProvider;
import org.goorm.everytime.member.domain.Authority;
import org.goorm.everytime.member.domain.Member;
import org.goorm.everytime.member.domain.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OAuth2LoginService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;

    @Value("${KAKAO_CLIENT_ID}")
    private String kakaoClientId;

    @Value("${KAKAO_CLIENT_SECRET}")
    private String kakaoClientSecret;

    @Value("${REDIRECT_URL_KAKAO}")
    private String redirectUrlKakao;

    @Value("${GOOGLE_CLIENT_ID}")
    private String googleClientId;

    @Value("${GOOGLE_CLIENT_SECRET}")
    private String googleClientSecret;

    @Value("${REDIRECT_URL_GOOGLE}")
    private String redirectUrlGoogle;

    private static final String AUTHORIZATION_CODE = "authorization_code";

    @Transactional
    public TokenDto proccessOAuth2Login(String provider, String code) {
        if (provider.equals(SocialType.KAKAO.toString())) {
            KakaoTokenDto kakaoTokenDto = getToken(code, kakaoClientId, kakaoClientSecret, redirectUrlKakao, "https://kauth.kakao.com/oauth/token", KakaoTokenDto.class);
            Map<String, Object> attributes = getUserInfo(kakaoTokenDto.accessToken(), "https://kapi.kakao.com/v2/user/me");
            return authenticateUser(new KakaoUserInfo(attributes));
        } else if (provider.equals(SocialType.GOOGLE.toString())) {
            GoogleTokenDto googleTokenDto = getToken(code, googleClientId, googleClientSecret, redirectUrlGoogle, "https://oauth2.googleapis.com/token", GoogleTokenDto.class);
            Map<String, Object> attributes = getUserInfo(googleTokenDto.accessToken(), "https://www.googleapis.com/userinfo/v2/me");
            return authenticateUser(new GoogleUserInfo(attributes));
        } else {
            throw new IllegalArgumentException("지원하지 않는 소셜 로그인입니다.");
        }
    }

    private <T> T getToken(String code, String clientId, String clientSecret, String redirectUri, String tokenUri, Class<T> responseType) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", AUTHORIZATION_CODE);
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);
        params.add("client_secret", clientSecret);
        ResponseEntity<T> response = RestClient.create().post()
                .uri(tokenUri)
                .accept(MediaType.APPLICATION_JSON)
                .body(params)
                .retrieve()
                .toEntity(responseType);
        return response.getBody();
    }

    private Map<String, Object> getUserInfo(String accessToken, String userInfoUri) {
        ResponseEntity<Map> response = RestClient.create().get()
                .uri(userInfoUri)
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .toEntity(Map.class);
        return response.getBody();
    }

    private TokenDto authenticateUser(OAuth2UserInfo oAuth2UserInfo) {
        Member member = memberRepository.findByEmail(oAuth2UserInfo.getEmail())
                .orElseGet(() -> memberRepository.save(Member.builder()
                        .email(oAuth2UserInfo.getEmail())
                        .username(oAuth2UserInfo.getProviderId())
                        .name(oAuth2UserInfo.getName())
                        .password(passwordEncoder.encode(oAuth2UserInfo.getEmail() + oAuth2UserInfo.getName()))
                        .socialType(SocialType.of(oAuth2UserInfo.getProvider()))
                        .authority(Authority.ROLE_USER)
                        .build()));

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(
                new UsernamePasswordAuthenticationToken(member.getUsername(), oAuth2UserInfo.getEmail() + oAuth2UserInfo.getName())
        );

        return tokenProvider.generateTokenDto(authentication);
    }
}