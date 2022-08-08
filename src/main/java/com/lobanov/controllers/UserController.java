package com.lobanov.controllers;

import com.lobanov.dto.UserDto;
import com.lobanov.models.User;
import com.lobanov.security.jwt.JwtUser;
import com.lobanov.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserDto> getUserInfo() {
        JwtUser user = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(userService.getUserById(user.getId()));
    }

    @PutMapping
    public ResponseEntity<UserDto> changeUserInfo(@RequestBody UserDto payload) {
        JwtUser user = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        payload.setId(user.getId());
        UserDto userDto = userService.updateUser(payload);
        return ResponseEntity.ok(userDto);
    }

}
