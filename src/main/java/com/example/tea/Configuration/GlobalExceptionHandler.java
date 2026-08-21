package com.example.tea.Configuration;

import com.example.tea.DTO.GenericResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Fallback handler so any exception not already caught in a controller is still
 * returned as a consistent {@link GenericResponse} (status=false) instead of a
 * raw stack trace / whitelabel error page.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public GenericResponse handleException(Exception e) {
        e.printStackTrace();
        return GenericResponse.error(e.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public GenericResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return GenericResponse.error(e.getFieldError().getDefaultMessage());
    }
}
