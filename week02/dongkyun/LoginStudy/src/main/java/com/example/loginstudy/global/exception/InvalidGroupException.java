package com.example.loginstudy.global.exception;

public abstract class InvalidGroupException extends RuntimeException{
    public InvalidGroupException(String message) {
        super(message);
    }
}
