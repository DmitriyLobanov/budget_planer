package com.lobanov.exсeptions;

import com.lobanov.dto.response.CustomResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

import static java.time.LocalDateTime.now;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(value = UserAlreadyExistException.class)
    public ResponseEntity<Object> handleUserAlreadyExistException(UserAlreadyExistException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status)
                .body(CustomResponse.builder()
                        .timeStamp(now())
                        .message(ex.getMessage())
                        .errors(Map.of("exception", ex.getClass().getName()))
                        .build()
        );
    }

    @ExceptionHandler(JwtAuthenticationException.class)
    public ResponseEntity<Object> handleJwtAuthenticationException(JwtAuthenticationException ex) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(status)
                .body(CustomResponse.builder()
                        .timeStamp(now())
                        .message(ex.getMessage())
                        .errors(Map.of("exception", ex.getClass().getName()))
                        .build()
                );
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<Object> handleCategoryNotFoundException(CategoryNotFoundException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status)
                .body(CustomResponse.builder()
                        .timeStamp(now())
                        .message(ex.getMessage())
                        .errors(Map.of("exception", ex.getClass().getName()))
                        .build()
        );
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status)
                .body(CustomResponse.builder()
                        .timeStamp(now())
                        .message(ex.getMessage())
                        .errors(Map.of("exception", ex.getClass().getName()))
                        .build()
                );
    }
}
