package com.lobanov.repositories;

import com.lobanov.dto.UserDto;
import com.lobanov.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> getUserById(Long id);
    Optional<User> findUserByUsername(String username);
}
