package com.goormthon.everytime.app.service.user;

import com.goormthon.everytime.app.domain.user.User;
import com.goormthon.everytime.app.dto.user.resDto.UserInfoResDto;
import com.goormthon.everytime.app.repository.UserRepository;
import com.goormthon.everytime.global.exception.CustomException;
import com.goormthon.everytime.global.exception.code.ErrorCode;
import com.goormthon.everytime.global.exception.code.SuccessCode;
import com.goormthon.everytime.global.template.ApiResTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserInfoService {

    private final UserRepository userRepository;

    public ApiResTemplate<UserInfoResDto> getUserInfo(Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND, ErrorCode.USER_NOT_FOUND.getMessage()));

        return ApiResTemplate.success(SuccessCode.GET_USER_INFO_SUCCESS, UserInfoResDto.from(user));
    }
}
