package com.lobanov.exeptions;

import com.lobanov.dto.response.CustomResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.ServletException;
import java.io.IOException;
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
}
