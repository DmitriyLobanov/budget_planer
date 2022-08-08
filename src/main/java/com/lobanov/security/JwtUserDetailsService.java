package com.lobanov.security;

import com.lobanov.dto.UserDto;
import com.lobanov.models.User;
import com.lobanov.security.jwt.JwtUser;
import com.lobanov.security.jwt.JwtUserFactory;
import com.lobanov.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    //На основании найденного User генерит jwt юзера, который в свою очередь является impl UserDeatials
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto user = userService.findUserByUsername(username);
        return JwtUserFactory.create(user);
    }
}
