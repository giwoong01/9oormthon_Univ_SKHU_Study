package com.example.goormthonlogin.auth.dto;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
@Data
@Builder
@AllArgsConstructor
public class Token { // AccessToken 값을 전달하기 위한 dto(데이터 전송 객체)
    @SerializedName("access_token")
    private String accessToken;
}
