package org.skhu.skhu9roomthonlogin.global.controller;

import lombok.RequiredArgsConstructor;
import org.skhu.skhu9roomthonlogin.global.dto.Token;
import org.skhu.skhu9roomthonlogin.global.template.RspTemplate;
import org.skhu.skhu9roomthonlogin.global.service.AuthLoginService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login/oauth2")
public class AuthLoginController {

    private final AuthLoginService authLoginService;

    @GetMapping("/code/google")
    public RspTemplate<Token> googleCallback(@RequestParam(name = "code") String code) {
        String googleAccessToken = authLoginService.getGoogleAccessToken(code);
        return authLoginService.loginOrSignUp(googleAccessToken);
    }
}
