package com.goormthon.everytime.app.controller.auth;

import com.goormthon.everytime.app.dto.auth.reqDto.SignUpReqDto;
import com.goormthon.everytime.app.service.auth.SignUpService;
import com.goormthon.everytime.global.template.ApiResTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "회원가입/로그인", description = "회원가입/로그인을 담당하는 api 그룹")
@RequestMapping("/user")
public class AuthController {

    private final SignUpService signUpService;

    @PostMapping("/join")
    @Operation(
            summary = "자체 회원가입",
            description = "사용자로부터 회원가입에 필요한 정보를 입력받아 유효성 검사를 수행한 후, 회원가입을 처리합니다.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "회원가입 성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청"),
                    @ApiResponse(responseCode = "409", description = "중복된 정보"),
                    @ApiResponse(responseCode = "500", description = "서버 오류 or 관리자 문의")
            }
    )
    public ResponseEntity<ApiResTemplate<Void>> signUp(@Valid @RequestBody SignUpReqDto reqDto) {
        ApiResTemplate<Void> data = signUpService.signUp(reqDto);
        return ResponseEntity.status(data.getStatus()).body(data);
    }
}
