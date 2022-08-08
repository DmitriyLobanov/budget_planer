package com.lobanov.dto;

import com.lobanov.enums.UserStatus;
import com.lobanov.models.Category;
import com.lobanov.models.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Data
@Builder
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String secondName;
    private String email;
    private String phoneNumber;
    private List<Role> roles;
    private UserStatus status;

    private List<Category> categories;
}
