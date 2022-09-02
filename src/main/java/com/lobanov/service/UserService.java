package com.lobanov.service;

import com.lobanov.dto.request.UserDtoRequest;
import com.lobanov.dto.response.UserDtoResponse;
import com.lobanov.exeptions.UserNotFoundException;
import com.lobanov.models.User;
import com.lobanov.repositories.UserRepository;
import com.lobanov.security.jwt.JwtUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private JwtUser mapUserToJwtUser(User user) {
        return JwtUser.builder()
                .Id(user.getId())
                .email(user.getEmail())
                .firstName(user.getUsername())
                .secondName(user.getSecondName())
                .password(user.getPassword())
                .phoneNumber(user.getPhoneNumber())
                .authorities(user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getRoles().name())).collect(Collectors.toList()))
                .build();
    }

    private UserDtoResponse mapUserToUserDto(User user) {
        return  UserDtoResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .username(user.getUsername())
                .secondName(user.getSecondName())
               // .password(user.getPassword())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                //.status(user.getStatus())
               // .roles(user.getRoles())
                .categories(user.getCategories())
                .build();
    }

    //private UserInformationDto

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
       return userRepository.findAll();
    }

    public UserDtoResponse getUserById(Long id) {
        Optional<User> user = userRepository.getUserById(id);
        if (user.isEmpty()) {
            return  null;
        }
        return mapUserToUserDto(user.get());
    }
    public JwtUser findUserByUsername(String username) {
        User user = userRepository.findUserByUsername(username).orElseThrow(() -> new UserNotFoundException("not found"));
        return mapUserToJwtUser(user);
    }



    public UserDtoResponse updateUser(UserDtoRequest payload) {
        log.info("INTO METHOD");
        User user = userRepository.getUserById(payload.getId()).get();
        log.info("GET USER");
        user.setPassword(payload.getPassword());
        user.setEmail(payload.getEmail());
        user.setFirstName(payload.getFirstName());
        user.setSecondName(payload.getSecondName());
        user.setPhoneNumber(payload.getPhoneNumber());
        userRepository.save(user);
        return mapUserToUserDto(user);
    }
}
