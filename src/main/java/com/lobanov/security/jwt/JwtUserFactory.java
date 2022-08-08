package com.lobanov.security.jwt;

import com.lobanov.dto.UserDto;
import com.lobanov.enums.UserStatus;
import com.lobanov.models.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {

    public JwtUserFactory() {

    }

    public static JwtUser create(UserDto user) {
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getSecondName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getPassword(),
                mapToGrantedAuthorities(new ArrayList<>(user.getRoles())),
                user.getStatus().equals(UserStatus.ACTIVE)
        );
    }
    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> userRoles) {
        return userRoles.stream()
                .map( (role) -> new SimpleGrantedAuthority(role.getRoles().name()))
                .collect(Collectors.toList());
    }
}
