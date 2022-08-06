package com.lobanov.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final JwtTokenFilter tokenFilter;

    //в самое начало надо добавить jwt token filter
    @Override
    public void configure(HttpSecurity builder) {
        builder.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
