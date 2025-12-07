package com.example.productapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for REST controllers.
 *
 * <p>This class catches common exceptions and returns clean,
 * structured error responses instead of default Spring errors.</p>
 */
@RestControllerAdvice
public class ApiExceptionHandler {

    /**
     * Handles invalid arguments thrown from the application.
     *
     * @param ex the thrown IllegalArgumentException
     * @return 400 Bad Request with a simple error message
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleBad(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
    }

    /**
     * Handles validation errors triggered by @Valid or @Validated.
     *
     * @param ex the validation exception
     * @return 400 Bad Request containing field → error message pairs
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(
                fe -> errors.put(fe.getField(), fe.getDefaultMessage())
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    /**
     * Fallback handler for any other unhandled exceptions.
     *
     * @param ex the thrown exception
     * @return 500 Internal Server Error with the exception message
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleOther(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", ex.getMessage()));
    }
}
