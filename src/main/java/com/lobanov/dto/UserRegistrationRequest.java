package com.lobanov.dto;

import com.lobanov.enums.UserStatus;
import com.lobanov.models.Category;
import com.lobanov.models.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationRequest {
    private String username;
    private String password;
    private String firstName;
    private String secondName;
    private String email;
    private String phoneNumber;
}
