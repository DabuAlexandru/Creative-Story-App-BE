package com.example.masterthesisbe.exception;

public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }
}
