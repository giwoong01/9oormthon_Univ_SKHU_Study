package org.goorm.everytime.auth.service;

import lombok.RequiredArgsConstructor;
import org.goorm.everytime.auth.domain.*;
import org.goorm.everytime.member.domain.Authority;
import org.goorm.everytime.member.domain.Member;
import org.goorm.everytime.auth.domain.SocialType;
import org.goorm.everytime.member.domain.repository.MemberRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // OAuth2 서비스 구분 (구글, 카카오 등)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // OAuth2User에서 제공하는 사용자 정보 (Google 또는 Kakao API 호출로 받아온 정보)
        Map<String, Object> attributes = oAuth2User.getAttributes();

        // 소셜 로그인 제공자에 따라 사용자 정보를 가져오는 로직
        OAuth2UserInfo oAuth2UserInfo;
        if (registrationId.equals("google")) {
            oAuth2UserInfo = new GoogleUserInfo(attributes);
        } else if (registrationId.equals("kakao")) {
            oAuth2UserInfo = new KakaoUserInfo(attributes);
        } else {
            throw new IllegalArgumentException("Unsupported provider: " + registrationId);
        }

        // 사용자 정보 추출
        String email = oAuth2UserInfo.getEmail();
        String name = oAuth2UserInfo.getName();

        // DB에서 사용자 확인 후 없다면 회원가입
        Member user = memberRepository.findByEmail(email)
                .orElseGet(() -> memberRepository.save(Member.builder()
                        .email(email)
                        .username(email)
                        .name(name)
                        .socialType(SocialType.of(oAuth2UserInfo.getProvider()))
                        .authority(Authority.ROLE_USER)
                        .build()));

        // PrincipalUserDetails 생성
        return new PrincipalDetails(user, oAuth2UserInfo);
    }
}
