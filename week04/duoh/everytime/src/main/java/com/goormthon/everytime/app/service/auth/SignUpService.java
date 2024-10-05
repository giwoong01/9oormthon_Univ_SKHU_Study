package com.goormthon.everytime.app.service.auth;

import com.goormthon.everytime.app.domain.user.University;
import com.goormthon.everytime.app.domain.user.User;
import com.goormthon.everytime.app.dto.auth.reqDto.SignUpReqDto;
import com.goormthon.everytime.app.repository.UserRepository;
import com.goormthon.everytime.global.exception.CustomException;
import com.goormthon.everytime.global.exception.code.ErrorCode;
import com.goormthon.everytime.global.exception.code.SuccessCode;
import com.goormthon.everytime.global.template.ApiResTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SignUpService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ApiResTemplate<Void> signUp(SignUpReqDto reqDto) {
        validateSignUpReq(reqDto);

        University university = University.fromDisplayName(reqDto.universityName());
        String encodedPassword = passwordEncoder.encode(reqDto.password());

        User user = reqDto.toEntity(encodedPassword, university);
        userRepository.save(user);

        return ApiResTemplate.success(SuccessCode.SIGNUP_USER_SUCCESS, null);
    }

    private void validateSignUpReq(SignUpReqDto reqDto) {
        if (userRepository.existsByEmail(reqDto.email())) {
            throw new CustomException(ErrorCode.EMAIL_ALREADY_EXISTS, ErrorCode.EMAIL_ALREADY_EXISTS.getMessage());
        }

        if (userRepository.existsById(reqDto.id())) {
            throw new CustomException(ErrorCode.ID_ALREADY_EXISTS, ErrorCode.ID_ALREADY_EXISTS.getMessage());
        }

        if (!reqDto.password().equals(reqDto.checkPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_MISSMATCH, ErrorCode.PASSWORD_MISSMATCH.getMessage());
        }
    }
}
