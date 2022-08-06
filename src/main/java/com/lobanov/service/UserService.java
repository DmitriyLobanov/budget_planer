package com.lobanov.service;

import com.lobanov.dto.CategoryDto;
import com.lobanov.exeptions.UserNotFoundException;
import com.lobanov.models.User;
import com.lobanov.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
       return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return null;
    }
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username).orElseThrow(() -> new UserNotFoundException("not found"));
    }

    /*public User addExpensesToCategory(CategoryDto category, Long expense) {

    }*/
}
