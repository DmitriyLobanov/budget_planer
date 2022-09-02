package com.lobanov.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationRequestDto {
    private String username;
    private String password;
    private String firstName;
    private String secondName;
    private String email;
    private String phoneNumber;
}
