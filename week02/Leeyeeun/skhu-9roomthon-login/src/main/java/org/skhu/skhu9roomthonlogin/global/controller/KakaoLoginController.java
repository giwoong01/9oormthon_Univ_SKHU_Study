package org.skhu.skhu9roomthonlogin.global.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.skhu.skhu9roomthonlogin.global.dto.KakaoUserInfoResponseDto;
import org.skhu.skhu9roomthonlogin.global.service.KakaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j // 로깅 기능을 추가
@RestController // REST API 컨트롤러
@RequiredArgsConstructor // final 필드의 생성자 자동 생성
@RequestMapping("") // 기본 URL 경로 설정
public class KakaoLoginController {

    private final KakaoService kakaoService; // KakaoService 주입

    @GetMapping("/callback") // "/callback" 경로의 GET 요청 처리
    public ResponseEntity<?> callback(@RequestParam("code") String code) {
        String accessToken = kakaoService.getAccessTokenFromKakao(code); // 인증 코드로 엑세스 토큰 가져오기

        KakaoUserInfoResponseDto userInfo = kakaoService.getUserInfo(accessToken); // 엑세스 토큰으로 사용자 정보 가져오기

        // 로그인 또는 회원가입 로직 추가 가능
        return new ResponseEntity<>(HttpStatus.OK); // 200 OK 응답 반환
    }
}
