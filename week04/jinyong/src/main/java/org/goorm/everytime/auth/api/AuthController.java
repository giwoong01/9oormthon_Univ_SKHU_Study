package org.goorm.everytime.auth.api;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.goorm.everytime.auth.api.dto.AuthReqDto;
import org.goorm.everytime.auth.service.AuthService;
import org.goorm.everytime.auth.api.dto.TokenDto;
import org.goorm.everytime.global.common.dto.BaseResponse;
import org.goorm.everytime.global.common.exception.SuccessCode;
import org.goorm.everytime.global.common.exception.model.RefreshTokenInvalidException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "로그인", description = "자체 로그인 진행 API")
    @Parameters({
            @Parameter(name = "username", description = "아이디", required = true),
            @Parameter(name = "password", description = "비밀번호", required = true)
    })
    @PostMapping("/login")
    public BaseResponse<TokenDto> login(@RequestBody AuthReqDto authReqDto){
        return BaseResponse.success(SuccessCode.USER_LOGIN_SUCCESS, authService.login(authReqDto));
    }

    @Operation(summary = "토큰 재발급", description = "자체 토큰 재발급 API, 헤더에서 Refresh Token을 추출한 후 진행한다.")
    @PostMapping("/refresh")
    public BaseResponse<TokenDto> refresh(HttpServletRequest request) throws RefreshTokenInvalidException {
        return BaseResponse.success(SuccessCode.USER_LOGIN_SUCCESS, authService.refresh(request));
    }
}
