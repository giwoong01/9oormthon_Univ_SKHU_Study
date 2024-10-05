package com.goormthon.everytime.global.exception;

import com.goormthon.everytime.global.exception.code.ErrorCode;
import com.goormthon.everytime.global.template.ApiResTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestControllerAdvice
public class ControllerExceptionAdvice {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ApiResTemplate<String>> handleCustomException(CustomException e) {
        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(ApiResTemplate.error(e.getErrorCode()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResTemplate<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, String> errorMap = new HashMap<>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errorMap.putIfAbsent(error.getField(), Optional.ofNullable(error.getDefaultMessage())
                    .orElse(ErrorCode.VALIDATION_ERROR.getMessage()));
        }

        return ResponseEntity
                .status(ErrorCode.VALIDATION_ERROR.getHttpStatus())
                .body(ApiResTemplate.<Map<String, String>>error(ErrorCode.VALIDATION_ERROR)
                        .toBuilder()
                        .data(errorMap)
                        .build());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResTemplate<String>> handleHttpMessageNotReadableException() {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResTemplate.error(ErrorCode.JSON_PARSE_ERROR));
    }
}
