package org.goorm.everytime.global.common.exception.model;

import lombok.Getter;

@Getter
public class RefreshTokenInvalidException extends Exception {
    public RefreshTokenInvalidException(String msg) {
        super(msg);
    }
}