package com.lobanov.controllers;

import com.lobanov.dto.UserRegistrationRequest;
import com.lobanov.security.AuthenticatedRequestDto;
import com.lobanov.service.AuthenticationRestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        String token = authenticationRestService.createToken(request.getUsername(), request.getPassword());
        if (token == null) {
            return new  ResponseEntity<>("Invalid username or password", HttpStatus.FORBIDDEN);
        }
        Map<Object, Object> response = new HashMap<>();
        response.put("username", request.getUsername());
        response.put("token", token);
        return ResponseEntity.ok(response);
    }

    //Performs a logout by modifying the SecurityContextHolder

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler handler = new SecurityContextLogoutHandler();
        handler.logout(request, response, null);
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody UserRegistrationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationRestService.createUser(request));
    }
}
