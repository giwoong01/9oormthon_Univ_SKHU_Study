package com.skhu.goormthon.global.exception;

import com.skhu.goormthon.global.exception.code.ErrorCode;
import com.skhu.goormthon.global.template.ApiResTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerExceptionAdvice {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ApiResTemplate<String>> handleCustomException(CustomException e) {
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(ApiResTemplate.error(e.getErrorCode()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResTemplate<String>> handleValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, String> errorMap = e.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage, (existing, replacement) -> existing));

        return ResponseEntity.status(ErrorCode.VALIDATION_EXCEPTION.getHttpStatus())
                .body(ApiResTemplate.<String>error(ErrorCode.VALIDATION_EXCEPTION)
                        .toBuilder()
                        .data(convertMapToString(errorMap))
                        .build());
    }

    private String convertMapToString(Map<String, String> map) {
        return map.entrySet().stream()
                .map(entry -> entry.getKey() + " : " + entry.getValue())
                .collect(Collectors.joining(", "));
    }
}
