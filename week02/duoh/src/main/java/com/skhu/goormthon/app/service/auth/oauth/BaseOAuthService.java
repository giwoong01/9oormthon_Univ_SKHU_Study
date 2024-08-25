package com.skhu.goormthon.app.service.auth.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skhu.goormthon.app.domain.member.LoginType;
import com.skhu.goormthon.app.domain.member.Member;
import com.skhu.goormthon.app.domain.member.RoleType;
import com.skhu.goormthon.app.dto.auth.resDto.AuthResDto;
import com.skhu.goormthon.app.dto.member.MemberInfoDto;
import com.skhu.goormthon.app.repository.MemberRepository;
import com.skhu.goormthon.app.service.auth.TokenRenewService;
import com.skhu.goormthon.global.exception.CustomException;
import com.skhu.goormthon.global.exception.code.ErrorCode;
import com.skhu.goormthon.global.exception.code.SuccessCode;
import com.skhu.goormthon.global.template.ApiResTemplate;
import com.skhu.goormthon.global.jwt.TokenProvider;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.Objects;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseOAuthService {

    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;
    private final TokenRenewService tokenRenewService;
    protected final RestClient restClient;
    protected final ObjectMapper objectMapper;

    protected abstract String getMemberInfoUrl();

    protected abstract LoginType getLoginType();

    protected abstract MemberInfoDto parseMemberInfo(JsonNode jsonNode, String accessToken);

    @Transactional
    public ApiResTemplate<AuthResDto> signUpOrLogin(String accessToken) {
        MemberInfoDto memberInfoDto = getMemberInfo(accessToken);

        return memberRepository.findByEmail(memberInfoDto.email())
                .map(existingMember -> {
                    if (existingMember.getLoginType() != getLoginType()) {
                        throw new CustomException(ErrorCode.ALREADY_EXIST_MEMBER_EXCEPTION,
                                ErrorCode.ALREADY_EXIST_MEMBER_EXCEPTION.getMessage());
                    }
                    return generateToken(existingMember, SuccessCode.LOGIN_MEMBER_SUCCESS);
                })
                .orElseGet(() -> {
                    Member newMember = Member.builder()
                            .name(memberInfoDto.name())
                            .email(memberInfoDto.email())
                            .password(null)
                            .loginType(getLoginType())
                            .roleType(RoleType.ROLE_USER)
                            .build();
                    memberRepository.save(newMember);
                    return generateToken(newMember, SuccessCode.SIGNUP_MEMBER_SUCCESS);
                });
    }

    private ApiResTemplate<AuthResDto> generateToken(Member member, SuccessCode successCode) {
        String accessToken = tokenProvider.createAccessToken(member);
        String refreshToken = tokenProvider.createRefreshToken(member);
        tokenRenewService.saveRefreshToken(refreshToken, member.getMemberId());

        return ApiResTemplate.success(successCode, AuthResDto.of(accessToken, refreshToken));
    }

    private MemberInfoDto getMemberInfo(String accessToken) {
        String result = restClient.get()
                .uri(getMemberInfoUrl())
                .headers(headers -> headers.set("Authorization", "Bearer " + accessToken))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    throw new CustomException(ErrorCode.NOT_FOUND_MEMBER_EXCEPTION,
                            ErrorCode.NOT_FOUND_MEMBER_EXCEPTION.getMessage());
                })
                .body(String.class);

        JsonNode jsonNode = parseJson(result);
        return parseMemberInfo(jsonNode, accessToken);
    }

    protected ApiResTemplate<String> getAccessToken(String url, HttpEntity<MultiValueMap<String, String>> requestEntity) {
        String result = restClient.post()
                .uri(url)
                .headers(headers -> headers.addAll(requestEntity.getHeaders()))
                .body(Objects.requireNonNull(requestEntity.getBody()))
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
                    throw new CustomException(ErrorCode.FAILED_GET_TOKEN_EXCEPTION,
                            ErrorCode.FAILED_GET_TOKEN_EXCEPTION.getMessage());
                })
                .body(String.class);

        JsonNode jsonNode = parseJson(result);
        if (jsonNode.has("access_token")) {
            return ApiResTemplate.success(SuccessCode.GET_TOKEN_SUCCESS, jsonNode.get("access_token").asText());
        }

        throw new CustomException(ErrorCode.FAILED_GET_TOKEN_EXCEPTION, ErrorCode.FAILED_GET_TOKEN_EXCEPTION.getMessage());
    }

    private JsonNode parseJson(String jsonString) {
        try {
            return objectMapper.readTree(jsonString);
        } catch (JsonProcessingException e) {
            throw new CustomException(ErrorCode.JSON_SYNTAX_EXCEPTION, ErrorCode.JSON_SYNTAX_EXCEPTION.getMessage());
        }
    }
}
