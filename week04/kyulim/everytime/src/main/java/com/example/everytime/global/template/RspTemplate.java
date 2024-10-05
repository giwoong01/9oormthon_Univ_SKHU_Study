package com.example.everytime.global.template;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
public class RspTemplate<T> {
    int statusCode;
    String message;
    T data;

    public RspTemplate(HttpStatus httpStatus, String message, T data) {
        this.statusCode = httpStatus.value();
        this.message = message;
        this.data = data;
    }

    public RspTemplate(HttpStatus httpStatus, String message) {
        this.statusCode = httpStatus.value();
        this.message = message;
    }
}

