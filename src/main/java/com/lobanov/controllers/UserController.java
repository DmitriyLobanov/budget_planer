package com.lobanov.controllers;

import com.lobanov.models.User;
import com.lobanov.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

}
