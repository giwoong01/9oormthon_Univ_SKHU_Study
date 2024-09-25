package com.example.everytime.global.error.dto;

public record ErrorResponse(
        int statusCode,
        String message
) {
}