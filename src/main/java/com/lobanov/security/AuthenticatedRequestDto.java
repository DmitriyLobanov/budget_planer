package com.lobanov.security;

import lombok.Data;

@Data
public class AuthenticatedRequestDto {
    private String username;
    private String password;
}
