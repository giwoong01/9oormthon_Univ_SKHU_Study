package com.example.loginstudy.global.exception;

public abstract class AuthGroupException extends RuntimeException{
    public AuthGroupException(String message) {
        super(message);
    }
}
