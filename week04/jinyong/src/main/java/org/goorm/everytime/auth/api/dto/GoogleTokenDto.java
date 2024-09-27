package org.goorm.everytime.auth.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GoogleTokenDto(

        @JsonProperty("access_token")
        String accessToken,

        @JsonProperty("token_type")
        String tokenType,

        @JsonProperty("expires_in")
        String expiresIn,

        @JsonProperty("scope")
        String scope,

        @JsonProperty("id_token")
        String idToken
) {
}
