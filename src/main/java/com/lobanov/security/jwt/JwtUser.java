package com.lobanov.security.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collection;
import java.util.List;

//с этим user-ом работает сам spring security
@RequiredArgsConstructor
@Getter
public class JwtUser implements UserDetails {

    private final Long Id;
    private final String username;
    private final String firsName;
    private final String secondName;
    private final String email;
    private final String phoneNumber;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private final boolean enabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
