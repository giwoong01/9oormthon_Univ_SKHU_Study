package org.goorm.everytime.member.api;

import lombok.RequiredArgsConstructor;
import org.goorm.everytime.global.common.dto.BaseResponse;
import org.goorm.everytime.global.common.exception.SuccessCode;
import org.goorm.everytime.member.domain.Member;
import org.goorm.everytime.member.service.ProfileService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/user/profile")
    public BaseResponse getUserProfile(Principal principal) {
        return BaseResponse.success(SuccessCode.USER_PROFILE_SUCCESS, profileService.getUserProfile(principal));
    }
}
