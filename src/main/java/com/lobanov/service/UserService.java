package com.lobanov.service;

import com.lobanov.dto.CategoryDto;
import com.lobanov.dto.UserDto;
import com.lobanov.exeptions.UserNotFoundException;
import com.lobanov.models.User;
import com.lobanov.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private UserDto mapUserToUserDto(User user) {
        return  UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .username(user.getUsername())
                .secondName(user.getSecondName())
                .password(user.getPassword())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .status(user.getStatus())
                .roles(user.getRoles())
                .categories(user.getCategories())
                .build();
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
       return userRepository.findAll();
    }

    public UserDto getUserById(Long id) {
        Optional<User> user = userRepository.getUserById(id);
        if (user.isEmpty()) {
            return  null;
        }
        return mapUserToUserDto(user.get());
    }
    public UserDto findUserByUsername(String username) {
        User user = userRepository.findUserByUsername(username).orElseThrow(() -> new UserNotFoundException("not found"));
        return mapUserToUserDto(user);
    }

    public UserDto updateUser(UserDto payload) {
        log.info("INTO METHOD");
        User user = userRepository.getUserById(payload.getId()).get();
        log.info("GET USER");

        user.setUsername(payload.getUsername());
        user.setPassword(payload.getPassword());
        user.setEmail(payload.getEmail());
        user.setFirstName(payload.getFirstName());
        user.setSecondName(payload.getSecondName());
        user.setPhoneNumber(payload.getPhoneNumber());
        userRepository.save(user);
        return mapUserToUserDto(user);
    }
}
