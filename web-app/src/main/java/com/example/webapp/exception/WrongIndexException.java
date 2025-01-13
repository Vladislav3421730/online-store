package com.example.webapp.exception;

public class WrongIndexException extends RuntimeException {
    public WrongIndexException(String message) {
        super(message);
    }
}
