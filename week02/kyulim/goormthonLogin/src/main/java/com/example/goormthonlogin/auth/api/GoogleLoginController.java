package com.example.goormthonlogin.auth.api;

import com.example.goormthonlogin.auth.application.GoogleLoginService;
import com.example.goormthonlogin.auth.dto.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login/oauth2")
public class GoogleLoginController {

    private final GoogleLoginService googleLoginService;

    @GetMapping("/code/google") // 인가 코드 받음
    public Token googleCallback(@RequestParam(name = "code") String code) {
        String googleAccessToken = googleLoginService.getGoogleAccessToken(code);
        return loginOrSignUp(googleAccessToken);
    }

    public Token loginOrSignUp(String googleAccessToken) { // 인가 코드를 통해 로그인이나 회원가입 하도록 함
        return googleLoginService.loginOrSignUp(googleAccessToken);
    }
}
