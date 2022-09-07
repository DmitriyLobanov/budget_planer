package com.lobanov.dto.response;

import com.lobanov.models.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
@Builder
public class UserDtoResponse {
    private Long id;
    private String username;
    private String firstName;
    private String secondName;
    private String email;
    private List<Category> categories;
    private String phoneNumber;
}
