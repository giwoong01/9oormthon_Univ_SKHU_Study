package com.example.goormthonloginstudy.global.error.dto;

public record ErrorResponse(
        int statusCode,
        String message
) {
}