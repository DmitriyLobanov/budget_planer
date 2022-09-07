package com.lobanov.controllers;

import com.lobanov.dto.request.UserDtoRequest;
import com.lobanov.dto.response.UserDtoResponse;
import com.lobanov.security.jwt.JwtUser;
import com.lobanov.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserDtoResponse> getUserInfo() {
        JwtUser user = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(userService.getUserById(user.getId()));
    }

    @PutMapping
    public ResponseEntity<UserDtoResponse> changeUserInfo(@RequestBody UserDtoRequest payload) {
        JwtUser user = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        payload.setId(user.getId());
        UserDtoResponse userDto = userService.updateUser(payload);
        return ResponseEntity.ok(userDto);
    }

}
