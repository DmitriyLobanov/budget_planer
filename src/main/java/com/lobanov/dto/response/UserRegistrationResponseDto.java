package com.lobanov.dto.response;

import lombok.Data;

@Data
public class UserRegistrationResponseDto {
    private String username;
    private String firstName;
    private String secondName;
    private String email;
    private String phoneNumber;
}
