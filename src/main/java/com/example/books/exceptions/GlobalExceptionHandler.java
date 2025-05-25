package com.example.books.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice

public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> handleTypeMismatch(MethodArgumentTypeMismatchException e) {
        Map<String, Object> errors = new HashMap<>();
        errors.put("error", "Invalid type for parameter: " + e.getName());
        errors.put("status", HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFoundException(ResourceNotFoundException e) {
        Map<String, Object> errors = new HashMap<>();
        errors.put("error", e.getMessage());
        errors.put("status", HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);

    }
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleAlreadyExistsException(ResourceAlreadyExistsException e) {
        Map<String, Object> errors = new HashMap<>();
        errors.put("error", e.getMessage());
        errors.put("status", HttpStatus.CONFLICT.value());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errors);
    }
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidCredentialsException(InvalidCredentialsException e) {
        Map<String, Object> errors = new HashMap<>();
        errors.put("error", e.getMessage());
        errors.put("status", HttpStatus.UNAUTHORIZED.value());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errors);
        
    }
    @ExceptionHandler(NotAllowedException.class)
    public ResponseEntity<Map<String, Object>> handleNotAllowedException(NotAllowedException e) {
        Map<String,Object> errors = new HashMap<>();
        errors.put("error", e.getMessage());
        errors.put("status", HttpStatus.FORBIDDEN.value());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errors);
    }
    @ExceptionHandler(DataConflictException.class)
    public ResponseEntity<Map<String, Object>> handleDataConflictException(DataConflictException e) {
        Map<String,Object> errors = new HashMap<>();
        errors.put("error", e.getMessage());
        errors.put("status", HttpStatus.CONFLICT.value());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception e) {
        Map<String, Object> errors = new HashMap<>();
        errors.put("error", "An unexpected error occurred: " + e.getMessage());
        errors.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errors);
    }
}

