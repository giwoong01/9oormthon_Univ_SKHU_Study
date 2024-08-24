package com.example.goormthonlogin.auth.api;

import com.example.goormthonlogin.auth.application.NaverLoginService;
import com.example.goormthonlogin.auth.dto.Token;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class NaverLoginController {

    private final HttpServletResponse response;
    private final NaverLoginService naverLoginService;

    @Value("${oauth.naver.client-id}")
    private String CLIENT_STRING;

    @GetMapping("/naver/authorize")
    public void naverAuthorize() throws IOException {
        String temp = "https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id="+CLIENT_STRING+"&state="+ generateCSRFToken()
                +"&redirect_uri=http://localhost:8080/login/naver";
        response.sendRedirect(temp);
    }

    @GetMapping("/naver") // 네이버 인가 코드 받음
    public Token naverCallback(@RequestParam(name = "code") String code,
                               @RequestParam(name = "state") String state) {
        String naverAccessToken = naverLoginService.getNaverAccessToken(code, state);
        return naverLoginService.loginOrSignUp(naverAccessToken);
    }

    private String generateCSRFToken() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] token = new byte[32]; // 32바이트의 랜덤 바이트 배열 생성
        secureRandom.nextBytes(token);
        return Base64.getUrlEncoder().encodeToString(token); // URL 인코딩
    }
}
