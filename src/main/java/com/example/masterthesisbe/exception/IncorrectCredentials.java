package com.example.masterthesisbe.exception;

public class IncorrectCredentials extends RuntimeException {
    public IncorrectCredentials() {
        super("Incorrect credentials! Please check again the email and the password!");
    }
}
