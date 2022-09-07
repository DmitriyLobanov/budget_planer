package com.lobanov.dto.request;

import lombok.Data;
import lombok.Getter;

@Data
public class UserDtoRequest {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String secondName;
    private String phoneNumber;
}
