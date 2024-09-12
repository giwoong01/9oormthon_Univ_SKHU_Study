package org.skhu.skhu9roomthonlogin.member.api.dto.request;
import jakarta.validation.constraints.NotBlank;
public record MemberSaveReqDto(
        @NotBlank
        String email,
        @NotBlank
        String pwd,
        @NotBlank
        String nickname
        ) {
}