package com.lobanov.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@Data
public class UserInformationDto {
    private String username;
    private String phoneNumber;
    private String email;
    private String firstName;
    private String secondName;
}
