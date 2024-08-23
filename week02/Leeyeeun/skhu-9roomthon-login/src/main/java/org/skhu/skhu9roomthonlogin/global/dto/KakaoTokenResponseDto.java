package org.skhu.skhu9roomthonlogin.global.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor // 역직렬화를 위한 기본 생성자
@JsonIgnoreProperties(ignoreUnknown = true) // JSON 필드가 DTO에 정의되지 않아도 무시
public class KakaoTokenResponseDto {

    @JsonProperty("token_type")
    public String tokenType; // 토큰 유형

    @JsonProperty("access_token")
    public String accessToken; // 엑세스 토큰

    @JsonProperty("id_token")
    public String idToken; // ID 토큰

    @JsonProperty("expires_in")
    public Integer expiresIn; // 엑세스 토큰 만료 시간(초 단위)

    @JsonProperty("refresh_token")
    public String refreshToken; // 리프레시 토큰

    @JsonProperty("refresh_token_expires_in")
    public Integer refreshTokenExpiresIn; // 리프레시 토큰 만료 시간(초 단위)

    @JsonProperty("scope")
    public String scope; // 토큰의 권한 범위
}
