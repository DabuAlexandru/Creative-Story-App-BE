package com.example.masterthesisbe.exception;

import com.example.masterthesisbe.dto.response.ExceptionDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionAdvice {
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ExceptionDto> handleApiException(ApiException e) {
        ExceptionDto exceptionResponse = new ExceptionDto(e.getMessage());
        return ResponseEntity.badRequest().body(exceptionResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> handleDefaultException(Exception e) {
        ExceptionDto exceptionResponse = new ExceptionDto(e.getMessage());
        return ResponseEntity.internalServerError().body(exceptionResponse);
    }
}
