package com.goormthon.everytime.global.template;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiResTemplate<T> {

    int status;
    String message;
    T data;

    public ApiResTemplate(HttpStatus httpStatus, String message, T data) {
        this.status = httpStatus.value();
        this.message = message;
        this.data = data;
    }

    public ApiResTemplate(HttpStatus httpStatus, String message) {
        this.status = httpStatus.value();
        this.message = message;
    }
}
