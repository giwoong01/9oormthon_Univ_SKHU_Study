package com.example.loginstudy.global.error.dto;

public record ErrorResponse(
        int statusCode,
        String message
) {
}