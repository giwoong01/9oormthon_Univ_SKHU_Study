package org.skhu.everytime.global.error.dto;

public record ErrorResponseDTO<T>(
        int statusCode,
        String message,
        T data
) {
}
