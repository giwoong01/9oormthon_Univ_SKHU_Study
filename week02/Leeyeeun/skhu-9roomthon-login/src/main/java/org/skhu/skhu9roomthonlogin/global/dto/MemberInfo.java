package org.skhu.skhu9roomthonlogin.global.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class MemberInfo {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("given_name")
    private String givenName;

    @SerializedName("family_name")
    private String familyName;

    @SerializedName("email")
    private String email;

    @SerializedName("verified_email")
    private Boolean verifiedEmail;

    @SerializedName("picture")
    private String picture;
}
