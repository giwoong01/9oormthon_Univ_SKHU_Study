package com.goormthon.everytime.app.service.auth;

import com.goormthon.everytime.app.domain.user.User;
import com.goormthon.everytime.app.dto.auth.reqDto.LoginReqDto;
import com.goormthon.everytime.app.dto.auth.resDto.AuthResDto;
import com.goormthon.everytime.app.repository.UserRepository;
import com.goormthon.everytime.global.exception.CustomException;
import com.goormthon.everytime.global.exception.code.ErrorCode;
import com.goormthon.everytime.global.exception.code.SuccessCode;
import com.goormthon.everytime.global.jwt.TokenProvider;
import com.goormthon.everytime.global.template.ApiResTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final TokenRenewService tokenRenewService;

    @Transactional
    public ApiResTemplate<AuthResDto> login(LoginReqDto reqDto) {
        User user = userRepository.findById(reqDto.id())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_ID_NOT_FOUND, ErrorCode.USER_ID_NOT_FOUND.getMessage()));

        if (!passwordEncoder.matches(reqDto.password(), user.getPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_MISSMATCH, ErrorCode.PASSWORD_MISSMATCH.getMessage());
        }

        String accessToken = tokenProvider.createAccessToken(user);
        String refreshToken = tokenProvider.createRefreshToken(user);
        tokenRenewService.saveRefreshToken(refreshToken, user.getUserId());

        return ApiResTemplate.success(SuccessCode.LOGIN_USER_SUCCESS, AuthResDto.of(accessToken, refreshToken));
    }
}
