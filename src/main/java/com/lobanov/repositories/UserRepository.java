package com.lobanov.repositories;

import com.lobanov.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    //@EntityGraph(attributePaths = {"roles", "categories"})
    Optional<User> getUserById(Long id);

   // @EntityGraph(attributePaths = {"roles", "categories"})

    Optional<User> findUserByUsername(String username);

    Optional<User> findUserByUsernameAndEmail(String username, String email);
}
