package com.lobanov.service;

import com.lobanov.dto.request.UserRegistrationRequestDto;
import com.lobanov.dto.response.UserRegistrationResponseDto;
import com.lobanov.enums.RolesEnum;
import com.lobanov.enums.UserStatus;
import com.lobanov.exeptions.JwtAuthenticationException;
import com.lobanov.models.User;
import com.lobanov.repositories.RoleRepository;
import com.lobanov.repositories.UserRepository;
import com.lobanov.security.jwt.JwtTokenProvider;
import com.lobanov.security.jwt.JwtUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationRestService {
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserRegistrationResponseDto mapRequestToResponse(UserRegistrationRequestDto requestDto) {
        return UserRegistrationResponseDto.builder()
                .username(requestDto.getUsername())
                .firstName(requestDto.getFirstName())
                .secondName(requestDto.getSecondName())
                .email(requestDto.getEmail())
                .phoneNumber(requestDto.getPhoneNumber())
                .build();
    }

    public String createToken(String username, String password) {
        Authentication authentication;
        String token;
        log.info("token creating with {} and {}", username, password);
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            log.info("authenticated");
            JwtUser user = (JwtUser) authentication.getPrincipal();
            token = jwtTokenProvider.createToken(username, user.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList()));
        } catch (AuthenticationException e) {
            throw new JwtAuthenticationException("Invalid Username or password");
        }
        return token;
    }

    //намутить фасад - маппер юзеров в дто и тд
    public User createUser(UserRegistrationRequestDto request) {
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setSecondName(request.getSecondName());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setUsername(request.getUsername());

        user.setStatus(UserStatus.ACTIVE);
        //Подправить
        user.setRoles(List.of(roleRepository.findByRoles(RolesEnum.ROLE_USER).get()));
        userRepository.save(user);
        return user;
    }


}
