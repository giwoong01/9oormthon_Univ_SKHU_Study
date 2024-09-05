package com.example.goormthonlogin.auth.dto;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
public class MemberInfo {

    @Data
    public static class GoogleMemberInfo{
        private String id;
        private String email;
        @SerializedName("verified_email")
        private Boolean verifiedEmail;
        private String name;
        @SerializedName("given_name")
        private String givenName;
        @SerializedName("family_name")
        private String familyName;
    }

    @Data
    public static class NaverMemberInfo{
        private String id;
        private String email;
        @SerializedName("profile_image")
        private String profile;
        private String name;
    }
    @Data
    public static class NaverMemberResult{
        private String resultcode;
        private String message;
        private NaverMemberInfo response;
    }

}
