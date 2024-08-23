package org.skhu.skhu9roomthonlogin.post.api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PostSaveReqDto(
        @NotBlank
        String title,
        @NotBlank
        String content
) {
}
