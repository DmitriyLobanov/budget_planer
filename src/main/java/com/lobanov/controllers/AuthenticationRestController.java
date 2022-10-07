package com.lobanov.controllers;

import com.lobanov.dto.request.UserRegistrationRequestDto;
import com.lobanov.dto.response.UserRegistrationResponseDto;
import com.lobanov.security.AuthenticatedRequestDto;
import com.lobanov.service.AuthenticationRestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationRestController {

    private final AuthenticationRestService authenticationRestService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticatedRequestDto request) {
        log.info("username {}, and password {}",request.getUsername(), request.getPassword());
        String token = authenticationRestService.createToken(request.getUsername(), request.getPassword());
        log.info("TOKEN IS {}", token);
        if (token == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid username or password");
        }
        Map<Object, Object> response = new HashMap<>();
        response.put("username", request.getUsername());
        response.put("token", token);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/registration")
    public ResponseEntity<UserRegistrationResponseDto> registration(@RequestBody UserRegistrationRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationRestService.createUser(request));
    }
}
